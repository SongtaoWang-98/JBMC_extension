#Requires python 3 and above

import subprocess, sys
import networkx as nx

#Extract class name and Compile Java program
classArray = sys.argv[1].split('.')
classname = classArray[0]
if len(classArray) == 2 and classArray[1] == 'java':
    subprocess.Popen(['javac', sys.argv[1]]).wait() 

#Run JBMC
cmd = 'jbmc ' + classname + ' --stop-on-fail --graphml-witness witness'
try:
    result = subprocess.check_output(cmd, shell=True)
except subprocess.CalledProcessError as e:
    result = e.output

#Check for violation
witnessFile = nx.read_graphml("witness")
violation = False
for violationKey in witnessFile.nodes(data=True):
    if 'isViolationNode' in violationKey[1]:
        violation = True

#Violation == True / Violation occurs
if violation:
    #Determine verifier variable type - only possible with .java/user supplied
    types = []
    if len(sys.argv) == 3:
        types.append(sys.argv[2].lower())
    else:
        with open(sys.argv[1], "rt") as fin:
             for line in fin:
                 index = line.find('Verifier.')
                 if index != -1:
                     types.append(line[index + 15 :].lower().split('(')[0])
                     
    if len(types) == 0:
        with open("ValidationHarnessTemplate.txt", "rt") as fin:
            with open("ValidationHarness.java", "wt") as fout:
                for line in fin:
                    line = line.replace('ClassName', classname)
                    fout.write(line)
    else:
        #Extract counterexample
        counterexamples = []
        for data in witnessFile.edges(data=True):
            if 'assumption' in data[2]:
                str = data[2]['assumption']
                #Get counterexample between ' = ' and ';'
                #E.g. "anonlocal::1i = 1000;"
                if str.startswith('anonlocal'):
                    counterexamples.append(str.split(' = ')[1][:-1])
    
        #Create validation harness from template
        with open("ValidationHarnessTemplate.txt", "rt") as fin:
            lines = []
            if(len(counterexamples) == 0):
                exit(1)
            for line in fin:
                line = line.replace('ClassName', classname)
                lines.append(line)
            for index in range(0, len(types)):
                type = types[index]
                counterexample = counterexamples[index]
                flag = 1
                if type == 'int':
                    for i in range(0,len(lines)):
                        if "Verifier.nondetInt()" in lines[i]:
                            lines[i] = lines[i].replace(";\n", ".thenReturn(" + counterexample + ");\n")
                            flag = 0
                    if flag:
                        lines.insert(-3, "    PowerMockito.when(Verifier.nondetInt()).thenReturn(" + counterexample + ");\n")
                if type == 'short':
                    for i in range(0,len(lines)):
                        if "Verifier.nondetShort()" in lines[i]:
                            lines[i] = lines[i].replace(";\n", ".thenReturn(" + counterexample + ");\n")
                            flag = 0
                    if flag:
                        lines.insert(-3, "    PowerMockito.when(Verifier.nondetShort()).thenReturn(" + counterexample + ");\n")
                if type == 'long':
                    for i in range(0,len(lines)):
                        if "Verifier.nondetLong()" in lines[i]:
                            lines[i] = lines[i].replace(";\n", ".thenReturn(" + counterexample + ");\n")
                            flag = 0
                    if flag:
                        lines.insert(-3, "    PowerMockito.when(Verifier.nondetLong()).thenReturn(" + counterexample + ");\n")
                if type == 'float':
                    for i in range(0,len(lines)):
                        if "Verifier.nondetFloat()" in lines[i]:
                            lines[i] = lines[i].replace(";\n", ".thenReturn(" + counterexample + ");\n")
                            flag = 0
                    if flag:
                        lines.insert(-3, "    PowerMockito.when(Verifier.nondetFloat()).thenReturn(" + counterexample + ");\n")
                if type == 'double':
                    for i in range(0,len(lines)):
                        if "Verifier.nondetDouble()" in lines[i]:
                            lines[i] = lines[i].replace(";\n", ".thenReturn(" + counterexample + ");\n")
                            flag = 0
                    if flag:
                        lines.insert(-3, "    PowerMockito.when(Verifier.nondetDouble()).thenReturn(" + counterexample + ");\n")
                if type == 'char':
                    for i in range(0,len(lines)):
                        if "Verifier.nondetChar()" in lines[i]:
                            lines[i] = lines[i].replace(";\n", ".thenReturn(" + '\'' + chr(int(counterexample)) + '\'' + ");\n")
                            flag = 0
                    if flag:
                        lines.insert(-3, "    PowerMockito.when(Verifier.nondetChar()).thenReturn(" + '\'' + chr(int(counterexample)) + '\'' + ");\n")
                if type == 'boolean':
                    if counterexample == '1':
                        value = "true"
                    else:
                        value = "false"
                    for i in range(0,len(lines)):
                        if "Verifier.nondetBoolean()" in lines[i]:
                            lines[i] = lines[i].replace(";\n", ".thenReturn(" + value + ");\n")
                            flag = 0
                    if flag:
                        lines.insert(-3, "    PowerMockito.when(Verifier.nondetBoolean()).thenReturn(" + value + ");\n")
                if type == 'string':
                    #try:
                    #    counterexample = int(counterexample)
                    #    string = "null"
                    #except ValueError:
                    string = '"' + counterexample + '"'
                    for i in range(0,len(lines)):
                        if "Verifier.nondetString()" in lines[i]:
                            lines[i] = lines[i].replace(";\n", ".thenReturn(" + string + ");\n")
                            flag = 0
                    if flag:
                        lines.insert(-3, "    PowerMockito.when(Verifier.nondetString()).thenReturn(" + string + ");\n")
                        
        with open("ValidationHarness.java", "wt") as fout:
            for line in lines:
                fout.write(line)

else:
    #Exit if no violation found by JBMC
    # print ('No violation found')
    # exit(1)
    with open("ValidationHarnessTemplate.txt", "rt") as fin:
        with open("ValidationHarness.java", "wt") as fout:
            for line in fin:
                line = line.replace('ClassName', classname)
                fout.write(line)
                
#Compile validation harness
subprocess.Popen(['javac', 'ValidationHarness.java']).wait()

#Execute validation harness
subprocess.Popen(['java', '-ea', 'org.junit.runner.JUnitCore' ,'ValidationHarness']).wait()
