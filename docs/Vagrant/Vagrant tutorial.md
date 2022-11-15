## Create the machine
```
mkdir ubuntu18
cd ubuntu18
vagrant init generic/ubuntu1804
```

## Start the machine
```
vagrant up
```

## Login the machine
```
vagrant ssh
```


## Logout the machine
```
exit
```
or
```
CTRL+D
```


## Suspend the machine

Suspending the virtual machine will stop it and save its current running state. Suspend the machine now.
```
vagrant suspend
```

## Halt the machine
Halting the virtual machine will gracefully shut down the guest operating system and power down the guest machine. Halt your machine now.
```
vagrant halt
```


## Destroy the machine

Destroying the virtual machine will remove all traces of the guest machine from your system. It'll stop the guest machine, power it down, and reclaim its disk space and RAM.
```
vagrant destroy
```

## List boxes
```
vagrant box list
```


> [Quik start](https://learn.hashicorp.com/tutorials/vagrant/getting-started-project-setup?in=vagrant/getting-started)

[HashiCorp's Vagrant Cloud box catalog](https://app.vagrantup.com/boxes/search)

[boxes create](https://www.vagrantup.com/vagrant-cloud/boxes/create)