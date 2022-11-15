In OSX I needed a shell script to execute at login. There are a few different ways to perform this so I thought it might be helpful to document what your options are. This is the shell script I will execute:

```bash
#!/bin/bash

function start() {
	echo ${PATH}
	echo "start mkdocs"
	# It should not append a & symbol to the end as urual to run the command in the background, cause we will let launch to take over the task.
	/Library/Frameworks/Python.framework/Versions/3.10/bin/mkdocs serve 
	 
}

function stop() {
	echo "stop mkdocs client..."
  ps -ef | grep "mkdocs" | awk  '{ print $2 }' | xargs  kill 
}

case ${1} in
  "start")
	start
  ;;
  "stop")
 	stop	
  ;;
  "restart")
	stop
	sleep 3
	start
  ;;
  "")
	start	
  ;;

  *)
  echo "error arguents"
  exit 1
  ;;
esac
```

This script  was named "[blogserver.sh](http://blogserver.sh)" locating in my blog diractory "\~/wk/blog/". It start my blogserver, so that I can browse my blogs located in my own cumputer in the browser. It should be noted that the services programe you are going to excute should not append a & symbol to the end as urual to daemonize , cause we will let launchd to take over the task. If you do this way, launchd will lose track of it and attempt to relaunch it.

The interface to launchd is a tool called launchctl which allows for loading and unloading daemons into launchd. XML formatted plist files are used to describe operations loaded into launchctl.

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN"
   "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
  <dict>
    <key>Label</key>
    <string>mkdocs.blogs</string>
    <key>ProgramArguments</key>
    <array>
      <string>./blogserver.sh</string>
      <string>start</string>
    </array>
    <key>WorkingDirectory</key>
    <string>/Users/wzq/wk/blog</string>
    <key>RunAtLoad</key>
    <true />
    <key>StandardErrorPath</key>
    <string>/Users/wzq/Library/Logs/myblogs.log</string>
    <key>StandardOutPath</key>
    <string>/Users/wzq/Library/Logs/myblogs.log</string>
  </dict>
</plist>
```

The above file **mkdocs.blogs.plist** needs to be saved in the **~/Library/LaunchAgents** directory. As you can see the xml dictates that the "[blogserver.sh](http://blogserver.sh)" file should be executed at load, note we no longer need a ".command" file extension. We will now use launchctl to load our plist file.

```
 launchctl load ~/Library/LaunchAgents/mkdocs.blogs.plist
```

To verify that your script executed correctly lets ask launchctl to show us what is running.

```bash
launchctl list | grep "mkdocs.blogs"
```

You will likely see an entries with three columns. Mine looks like this:

```
758	0	mkdocs.blogs
```

The first column is the process id. The second column displays the last exit status of the job, zero represent success.

## Property list

A list of often used keys follows below. All keys are optional unless otherwise noted. For a full list, see Apple's manpage for launchd.plist.

| Key | Type | Description |
|:---:|:---:|:---|
| Label | String | The name of the job. By convention, the job label is the same as the plist file name, without the .plist extension. Required. |
| Program | String | A path to an executable. Useful for simple launches. At least one of Program or ProgramArguments is required. |
| ProgramArguments | Array of strings | An array of strings representing a UNIX command. The first string is generally a path to an executable, while latter strings contain options or parameters. At least one of Program or ProgramArguments is required. |
| UserName | String <br>(defaults to root or current user) | The job will be run as the given user, who may (or may not) be the user who submitted it to launchd. |
| RunAtLoad | Boolean <br> (defaults to NO) | A boolean flag that defines if a task is launched immediately when the job is loaded into launchd. |
| StartOnMount | Boolean<br> (defaults to NO) | A boolean flag that defines if a task is launched when a new filesystem is mounted. |
| QueueDirectories | Array of strings | Watch a directory for new files. The directory must be empty to begin with, and must be returned to an empty state before QueueDirectories will launch its task again. |
| WatchPaths | Array of strings | Watch a filesystem path for changes. Can be a file or folder. |
| StartInterval | Integer | Schedules job to run on a repeating schedule. Indicates number of seconds to wait between runs. |
| StartCalendarInterval | Dictionary of integers or Array of dictionaries of integers | Job scheduling. The syntax is similar to [cron](https://en.wikipedia.org/wiki/Cron). |
| RootDirectory | String | The job will be chrooted into this directory before execution. |
| WorkingDirectory | String | The job will be chdired into this directory before execution. |
| StandardInPath,<br>StandardOutPath,<br>StandardErrorPath | String | Keys to determine files for input and output for the launched process. |
| LowPriorityIO | Boolean | Tells the kernel that this task is of a low priority when doing filesystem I/O. |
| AbandonProcessGroup | Boolean <br>(defaults to NO) | A boolean flag that defines whether subprocesses launched from a task launched by launchd will be killed when the task ends. Useful where a short-lived task starts a long-lived subtask, but may result in zombie processes. |
| SessionCreate | Boolean<br>(defaults to NO) | A boolean flag that defines whether a security session will be created for the task and its subprocesses. |

## Reference

> * <https://developer.apple.com/library/archive/documentation/MacOSX/Conceptual/BPSystemStartup/Chapters/CreatingLaunchdJobs.html##//apple_ref/doc/uid/10000172i-SW7-BCIEDDBJ>

* <https://en.wikipedia.org/wiki/Launchd>

\
