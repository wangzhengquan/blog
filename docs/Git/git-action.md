# Automatically Deploy React Project to Your Own Server Using GitHub Actions

Deploying your React project automatically to your own server using GitHub Actions involves setting up a CI/CD pipeline that will build your project and then transfer the build files to your server. This is often done using SSH for secure file transfer. Here's a step-by-step guide to help you set this up.

## Prerequisites

1. **SSH Access**: Make sure you have SSH access to your server.
2. **Node.js**: Ensure Node.js is installed on your server.
3. **Web Server**: Ensure a web server (like Nginx or Apache) is configured to serve your React application.


## Step-by-Step Guide

1. Set Up SSH Keys

First, you need to set up SSH keys for passwordless access from GitHub Actions to your server.

- Generate an SSH key pair on your local machine:
```bash
ssh-keygen -t rsa -b 4096 -C "your_email@example.com"
```
Save the key without a passphrase.

- Copy the public key to your server:

```
ssh-copy-id -i ~/.ssh/id_rsa.pub user@your-server-ip
```
- Add the private key to your GitHub repository as a secret:
	- Go to your GitHub repository.
	- Click on Settings > Secrets > New repository secret.
	- Add a new secret named SSH_PRIVATE_KEY and paste the content of your ~/.ssh/id_rsa file.

2. Create a Deployment Script on Your Server
Create a script on your server that will handle the deployment. For example, create a file named deploy.sh in your home directory:

```bash
#!/bin/bash

# Navigate to the web directory
cd /path/to/your/web/directory

# Remove old build
rm -rf build

# Unzip new build
unzip /path/to/uploaded/build.zip -d build

# Restart your web server (if necessary)
sudo systemctl restart nginx
```
Make sure this script is executable:
```bash
chmod +x ~/deploy.sh
```

3. Create GitHub Action Workflow
Create a file named deploy.yml in the .github/workflows directory of your React project:
```bash
name: Deploy to Server

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout repository
      uses: actions/checkout@v2

    - name: Set up Node.js
      uses: actions/setup-node@v2
      with:
        node-version: '14'

    - name: Install dependencies
      run: npm install

    - name: Build project
      run: npm run build

    - name: Archive build files
      run: zip -r build.zip build

    - name: Copy files via SSH
      uses: appleboy/scp-action@v0.1.0
      with:
        host: ${{ secrets.SERVER_IP }}
        username: ${{ secrets.SERVER_USER }}
        key: ${{ secrets.SSH_PRIVATE_KEY }}
        source: "build.zip"
        target: "/path/to/uploaded/build.zip"

    - name: Execute deployment script
      uses: appleboy/ssh-action@v0.1.0
      with:
        host: ${{ secrets.SERVER_IP }}
        username: ${{ secrets.SERVER_USER }}
        key: ${{ secrets.SSH_PRIVATE_KEY }}
        script: "~/deploy.sh"
```

4. Add GitHub Secrets

Add the following secrets to your GitHub repository:
- SERVER_IP: The IP address of your server.
- SERVER_USER: The username to log in to your server.
- SSH_PRIVATE_KEY: The private SSH key you generated earlier.

5. Push Workflow File

Commit and push the deploy.yml file to your GitHub repository:
```
git add .github/workflows/deploy.yml
git commit -m "Add deployment workflow"
git push
```

## Conclusion
With this setup, every time you push changes to the main branch, GitHub Actions will automatically build your React project, transfer the build files to your server, and run a deployment script to update your application. This ensures that your latest changes are always live on your server.