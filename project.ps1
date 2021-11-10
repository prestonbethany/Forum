<#
 # Project Maker for Deploying a Webapp to a Local Machine
 # Written by: Preston Bethany
 # 
 # Usage notes can be accessed by simply running the script with no args or
 # by running it with the help argument.
 #
 # When adding a new command to the script, make sure to add the commands and
 # their shortcuts to the array of valid commands checked at the start of the
 # script as well as adding the usage notes to the soft exit function.
 #>


# Script Variables #
$appFolderName = "Forum"
<#Before changing this, make sure you've configured MariaDB's my.ini to contain your username and password!!!#>
$mariaDBLocation = "..\mariadb-10.6.4-winx64\bin\"

#Script Globals #

#Functions

<#
 # Displays the usage information.
 #>
 function softExit {
    "Project maker for web apps."
    "Written by: Preston Bethany"
    "`nUsage notes:"
    "h`thelp`t`tDisplays this message."
    "dbstart`t`tStarts up MariaDB.(DO NOT USE IF YOU HAVE NOT CONFIGURED MariaDB's my.ini!!!)"
    "dbstop`t`tSafely shuts down MariaDB (Requires MariaDB's my.ini file to be configured with your username and password)."
    "fet`tfetchlibs`tDownload project dependancies."
    "d`tdeploy`t`tDeploys the application to the tomcat server."
    "del`tdelete`t`tDeletes the entire application from Tomcat."
    "st`tstart`t`tStarts the tomcat server."
    "dbg`tdebug`t`tStarts the tomcat server in debug mode (Requires user-created debug file)."
    "sh`tshutdown`tShuts the tomcat sever down."
    "cln`tclean`t`tDelete all compiled sources."
    "cmp`tcompile`t`tCompiles the project."
    "war`tArchives the project in a WAR file."
    "`nExample:`n.\project.ps1 clean compile "
    "`nThis will delete the binaries in the build directory, then compile the project."
    exit
}

# Script Start

# Ensure valid commands
if ($args.Length -lt 1) {
    softExit
}
foreach ($option in $args) {
    if (-Not (@(
        # Place your command name and shortcut here or it will not work
        'cln', 'clean',
        'cmp', 'compile',
        'war',
        'fet', 'fetchlibs', 
        'd', 'deploy',
        'st', 'start',
        'dbg', 'debug',
        'sh', 'shutdown',
        'del', 'delete',
        'dbstart',
        'dbstop'
        ).Contains($option))){
        softExit
    }
}

# Process commands in order
foreach($option in $args) {
    switch ($options) {

        #MariaDB Start
        {($option -eq "dbstart")}{
            "Starting MariaDB..."
            Push-Location $mariaDBLocation
            <#Start-Process ensures that MariaDB opens in a new instance of Powershell. #>
            Start-Process .\mariadbd.exe --console
            Pop-Location
        }

        #MariaDB Shutdown
        {($option -eq "dbstop")}{
            "Stopping MariaDB..."
            Push-Location $mariaDBLocation
            .\mariadb-admin.exe shutdown
            Pop-Location
        }
        

        #Clean
        {($option -eq "cln") -or ($option -eq "clean")} {
            "Deleting all compiled sources..."
            
            $destination = "webcontent\WEB-INF\classes\*"
            if ((Test-Path $destination)) {
                Remove-Item -Recurse -Force $destination
            }
            "Done!"
        }

        #Compile 
        {($option -eq "cmp") -or ($option -eq "compile")} {
            "Please do not exit your terminal, writing to files..."
            Push-Location ".\src\"
            Set-Content -Encoding "Ascii" -Path .\options.txt -Value "-classpath " -NoNewline
            <#Get-ChildItem collects the requested directory contents in a string array. 
            The FullName method ensures that these are recorded in the array as absolute paths. 
            -Join takes the array and concatenates each string together as one string,
            separated by the operator provided (in this case, a semicolon).#>
            Add-Content -Encoding "Ascii" -Path .\options.txt -Value ((Get-ChildItem -Path "..\webcontent\WEB-INF\lib", "..\..\apache-tomcat-9.0.43\lib" -Recurse -Include *.jar).FullName -Join ";")
            Set-Content -Encoding "Ascii" .\sources.txt "-d ..\webcontent\WEB-INF\classes\"
            Get-ChildItem -Path ".\" -Recurse -Include *.java -Name | Add-Content -Encoding "Ascii" .\sources.txt
            "Compiling..."
            & javac "@options.txt" "@sources.txt" 
            Pop-Location
            "Done!"
        }

        #Deploy
        {($option -eq "d") -or ($option -eq "deploy")}{
            "Deploying to tomcat server..."
            Copy-Item -Path ".\cfg\*" -Destination ".\webcontent\WEB-INF\classes" -Recurse -Force
            $destination = ($env:CATALINA_HOME + "\webapps\" + $appFolderName)
            <# specifying * after the folder name tells Copy-Item to only copy the contents of webcontent #>
            if (-Not (Test-Path $destination)) {
                New-Item $destination -ItemType "directory" | Out-Null
            }
            Copy-Item -Path ".\webcontent\*" -Destination $destination -Recurse -Force
            "Done!"
        }

        #Start
        {($option -eq "st") -or ($option -eq "start")}{
            "Starting Tomcat..."
            <# & specifies I want to run the bat file inside the current powershell session #>
            & ($env:CATALINA_HOME + "\bin\startup.bat")
        }

        #Debug
        {($option -eq "dbg") -or ($option -eq "debug")}{
            "Starting Tomcat in debug mode..."
            <# & specifies I want to run the bat file inside the current powershell session #>
            & ($env:CATALINA_HOME + "\bin\debug.bat")
        }

        #Shutdown
        {($option -eq "sh") -or ($option -eq "shutdown")}{
            "Stopping Tomcat..."
            & ($env:CATALINA_HOME + "\bin\shutdown.bat")
        }

        #Delete
        {($option -eq "del") -or ($option -eq "delete")}{
            "Waiting 3 seconds in case server shutdown is in progress..."
            Start-Sleep -Seconds 3
            "Deleting app from Tomcat..."
            if (Test-Path ($env:CATALINA_HOME + "\webapps\" + $appFolderName)) {
                Remove-Item ($env:CATALINA_HOME + "\webapps\" + $appFolderName) -Recurse
                "Done!"
            } else {
                ("Error: Webapp `"" + $appFolderName + "`" not found on Tomcat server! Delete cancelled!")
            }
        }

        #FetchLibs
        {($option -eq "fet") -or ($option -eq "fetchlibs")}{
            if (-Not (test-path ("webcontent\WEB-INF\lib"))) {
                mkdir "webcontent\WEB-INF\lib"
            }
            "Fetching app dependencies..."
        }

        #WAR
        {($option -eq "war")} {
            "Archiving compiled web sources..."
            $destination = (".\war\")
            if (-Not (Test-Path $destination)) {
                New-Item $destination -ItemType "directory" | Out-Null
            } 
            Push-Location ".\build\"
            & jar cvfe "..\war\forum.war" forum "*.class"
            Pop-Location 
            "Done!"
        }
    }
}
