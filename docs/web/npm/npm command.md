## Finding the path to the global node_modules folder

| Package Manager | Command to Find Global `node_modules` Path   |
| :-------------- | :------------------------------------------- |
| **npm**         | `npm root -g`                                |
| **pnpm**        | `pnpm root -g`                               |
| **Yarn**        | `yarn global dir` (returns parent directory) |

## Find NodeJS home
```bash
npm config get prefix
```

## Initialize a package.json file with default values
```bash
npm init -y
```

##  Cleaning Up node_modules
Sometimes your node_modules directory might contain packages that are no longer listed in your package.json (this can happen if you manually edit the file). To remove these extraneous or "pruned" packages, you can use:
```bash
pnpm prune
```

This command will scan your package.json and remove any packages found in node_modules that are not listed as a dependency. It's a great way to keep your node_modules clean and lean.
