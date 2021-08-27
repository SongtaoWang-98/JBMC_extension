#Requires python 3 and above

import subprocess, sys
import os
import shutil
import glob
import yaml
import webbrowser
            
with open("result.html", 'w') as r:
    r.write("""
<html>
<table border="1">
<tr>
<th>NO</th>
<th>Test suite</th>
<th>Title name</th>
<th>Type</th>
<th>Correct output</th>""")

num = 0
set_path = sys.argv[1]
option = sys.argv[2]
if(option == "JBMC" or option == "Both"):
    with open("result.html", 'a') as r:
        r.write("""<th>JBMC output</th>""")
if(option == "Witness" or option == "Both"):
    with open("result.html", 'a') as r:
        r.write("""<th>Witness output</th>""")
with open("result.html", 'a') as r:
    r.write("""<th>Comment</th></tr>""")
with open(set_path, 'r') as sets:
    for set_name in sets:
        ymls = glob.glob('/'.join(set_path.split('/')[:-1]) + '/' + set_name.split()[0])
        ymls.sort()
        for yml in ymls:
            print(yml)
            with open(yml, 'r') as y:
                yml_dic = yaml.load(y, Loader = yaml.FullLoader)
                if(yml_dic['properties'][0]['expected_verdict'] == True):
                    correct_result = "True"
                else:
                    correct_result = "False"
                path = '/'.join(yml.split('/')[:-1]) + '/' + yml_dic['input_files'][1]
                print(path)
                print(correct_result)

                # Compile
                java_list = os.listdir(path)
                for file_name in java_list:
                    if os.path.isfile(path + file_name):
                        shutil.copy(path + file_name, os.getcwd())
                    elif os.path.isdir(path):
                        print(path+file_name)
                        print(os.getcwd())
                        shutil.copytree(path + file_name, os.getcwd() + '/' + file_name)
                subprocess.Popen(['javac', "Main.java"]).wait()
                print("Compiling successfully!")

                # Execute JBMC
                if(option == "JBMC" or option == "Both"):
                    timeout = False
                    fjout = open("log/" + yml.split('/')[-1].split('.')[0] + "JBMC_file_out.log", 'w')
                    fjerr = open("log/" + yml.split('/')[-1].split('.')[0] + "JBMC_err_out.log", 'w')
                    try:
                        subprocess.call(["jbmc", "Main", "--stop-on-fail"], stdout=fjout, stderr=fjerr, timeout= 10)
                    except subprocess.TimeoutExpired as e:
                        print("Time out!\n")
                        timeout = True
                    with open("log/" + yml.split('/')[-1].split('.')[0] + "JBMC_file_out.log", 'r') as f:
                        lines = f.readlines()
                        if "FAIL" in lines[-1]:
                            jbmc_result = "False"
                        elif "SUCCESSFUL" in lines[-1]:
                            jbmc_result = "True"
                        else:
                            jbmc_result = "Unknown"
                        print("JBMC executed successfully! Result is: " + jbmc_result)

                # Check type
                data_type = ''
                flag = False
                with open("Main.java", "r") as fin:
                    for line in fin:
                        if line.find('Verifier.') >= 0 and data_type != '':
                            flag = True
                            break
                        index = line.find('Verifier.')
                        if index != -1:
                            data_type = line[index + 15:].lower().split('(')[0]

                if(option == "Witness" or option == "Both"):

                # Execute witness tool
                    fsout = open("log/" + yml.split('/')[-1].split('.')[0] + "script_file_out.log", 'w')
                    fserr = open("log/" + yml.split('/')[-1].split('.')[0] + "script_err_out.log", 'w')
                    try:
                        subprocess.call(["python3", "exec.py", "Main.java"], stdout=fsout, stderr=fserr, timeout=10)
                    except subprocess.TimeoutExpired as e:
                        print("Time out!")
                    with open("log/" + yml.split('/')[-1].split('.')[0] + "script_file_out.log", 'r') as f:
                        slines = f.readlines()
                        if len(slines)>3:
                            if "FAIL" in slines[-3]:
                                script_result = "False"
                            elif "OK" in slines[-2]:
                                script_result = "True"
                            else:
                                script_result = "Unknown"
                        else:
                            script_result = "Unknown"
                        print("exec.py executed successfully! Result is: " + script_result)

                # Add comments
                comment = ''
                if "Null pointer" in lines[-5]:
                    comment = "Null pointer exception"
                if data_type == '':
                    comment = "No verifier type"
                if flag:
                    dada_type = ''
                    comment = "Multiple verifiers"
                if timeout:
                    comment = "Execution time out"

                # Record results in table
                num = num + 1
                jbmc_correct = True
                script_correct = True
                if(option == "JBMC" or option == "Both"):
                    if jbmc_result != correct_result:
                        jbmc_correct = False
                if(option == "Witness" or option == "Both"):
                    if script_result != correct_result:
                        script_correct = False

                with open("result.html", 'a') as r:
                    r.write("""<tr>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td><font color="blue">%s</td>
                    """%(num, path.split('/')[-3], path.split('/')[-2], data_type, correct_result))
                    if(option == "JBMC" or option == "Both"):
                        if(jbmc_correct == True): r.write("""<td><font color="green">%s</td>"""%jbmc_result)
                        elif(jbmc_result != "Unknown"): r.write("""<td><font color="red">%s</td>"""%jbmc_result)
                        else: r.write("""<td><font color="orange">%s</td>"""%jbmc_result)
                    if(option == "Witness" or option == "Both"):
                        if(script_correct == True): r.write("""<td><font color="green">%s</td>"""%script_result)
                        elif(jbmc_result != "Unknown"): r.write("""<td><font color="red">%s</td>"""%script_result)
                        else: r.write("""<td><font color="orange">%s</td>"""%script_result)

                    r.write("""<td>%s</td></tr>"""%(comment))

                print("Results have been saved in .txt file.\n\n")

                # Delete useless files
                for file_name in java_list:
                    if ".java" in file_name:
                        os.remove(file_name)
                class_list = os.listdir(os.getcwd())
                for class_name in class_list:
                    if ".class" in class_name:
                        os.remove(class_name)

with open("result.html", 'a') as r:
    r.write("""</table></html>""")
print("Execution finished!")
#webbrowser.open("result.html")
shutil.copy(os.getcwd() + "/result.html", os.getcwd() + "/templates")
            
