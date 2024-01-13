## build & install
run the [go build](https://pkg.go.dev/cmd/go#hdr-Compile_packages_and_dependencies) command to compile the code into an executable
```
go build
```
run the [go install](https://go.dev/ref/mod#go-install) command to compile and install the package
```
go install
```
## run your code 
```
go run .
go run -race .
```

## module
choose a module path (we'll use example.com/greetings) and create a go.mod file that declares it, Run the [go mod init command](https://go.dev/ref/mod#go-mod-init) 
```
go mod init example.com/greetings
```

The [go mod tidy command](https://go.dev/ref/mod#go-mod-tidy) adds missing module requirements for imported packages and removes requirements on modules that aren't used anymore.
```
go mod tidy
```
Module dependencies are automatically downloaded to the pkg/mod subdirectory of the directory indicated by the GOPATH environment variable. The downloaded contents for a given version of a module are shared among all other modules that require that version, so the go command marks those files and directories as read-only. 


Use the [go mod edit command](https://go.dev/ref/mod#go-mod-edit) to edit the example.com/hello module to redirect Go tools from its module path (where the module isn't) to the local directory (where it is).
```
go mod edit -replace example.com/greetings=../greetings
```

To remove all downloaded modules, you can pass the -modcache flag to go clean:
```
go clean -modcache
```

## build .so
go build -buildmode=plugin plugin_name.go 


## The install directory is controlled by the GOPATH and GOBIN environment variables.
You can use the [go env command](https://pkg.go.dev/cmd/go#hdr-Print_Go_environment_information) to portably set the default value for an environment variable for future go commands:
```
go env -w GOBIN=/somewhere/else/bin
```

To unset a variable previously set by go env -w, use go env -u:
```
go env -u GOBIN
```

add the install directory to our PATH to make running binaries easy:

```
export PATH=$PATH:$(dirname $(go list -f '{{.Target}}' .))
```

## Reference
> https://pkg.go.dev/cmd/go