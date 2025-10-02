
Preloading Chinese fonts is a much more complex challenge due to the sheer number of characters, and it requires a different approach than Latin or Cyrillic.

### The Core Problem: You Can't Use `next/font/google`

The `next/font/google` loader and the Google Fonts API **do not directly serve Chinese, Japanese, or Korean (CJK) fonts**.

**Why? File Size.**

*   A Latin font file might contain a few hundred characters (glyphs) and be around 20-50 KB.
*   A full Chinese font can contain **over 20,000 characters** and be anywhere from **5 MB to 20 MB** in size.

Forcing a user to download a 10 MB font file just to see the page is not feasible for web performance. Therefore, you cannot simply write `subsets: ['chinese']`.

### The Solution: Local Fonts + Manual Subsetting

The best practice is to host the font file yourself and create a highly optimized **subset** of it that contains *only the characters you actually use on your site*. You then use the `next/font/local` loader.

Here is the step-by-step professional workflow:

---

### Step 1: Get the Font File

First, you need to download a high-quality Chinese font. You can get these from various sources. A very popular and excellent open-source choice is **Noto Sans SC** (Simplified Chinese) from Google Fonts.

1.  Go to [Google Fonts](https://fonts.google.com/).
2.  Search for "Noto Sans SC".
3.  Click the "Download family" button. This will give you a ZIP file containing the font in `.ttf` or `.otf` format.



### Step 2: Create a Character List

This is the most critical step for optimization. You need to gather **every unique Chinese character** that appears in the static text of your website (headings, buttons, paragraphs, etc.).

Create a text file, for example `characters.txt`, and paste all the unique characters into it.

**Example `characters.txt`:**
```
你好世界欢迎光临我的网站这是一个示例
```
*   **Pro Tip:** For a large site, you can write a script to crawl your source files (e.g., `.tsx`, `.mdx`) and extract all the CJK characters to generate this list automatically.

### Step 3: Subset the Font File

Now you'll use a command-line tool to create a new, much smaller font file that only contains the glyphs for the characters in your list. A great tool for this is `subset-font`.

1.  **Install the tool:**
    ```bash
    npm install -g subset-font
    ```

2.  **Run the subsetting command:**
    Let's say you downloaded `NotoSansSC-Regular.otf` and have your `characters.txt` file.

    ```bash
    subset-font NotoSansSC-Regular.otf characters.txt --output-file=./public/fonts/NotoSansSC-subset.woff2 --target-format=woff2
    ```

    **What this command does:**
    *   `NotoSansSC-Regular.otf`: The original, large font file.
    *   `characters.txt`: Your list of required characters.
    *   `--output-file=./public/fonts/NotoSansSC-subset.woff2`: Creates the new, small font file in the `woff2` format (best for web) inside your `public/fonts` directory.

The resulting `NotoSansSC-subset.woff2` file will be tiny—likely just a few kilobytes instead of megabytes!

### Step 4: Use `next/font/local` in Your App

Now you can use this local, subsetted font file in your Next.js application. `next/font/local` will handle all the preloading and performance optimization for you, just like `next/font/google` did before.

```javascript
// In your layout.js, _app.js, or a shared constants file

import localFont from 'next/font/local';

// Configure the local font
export const notoSansSC = localFont({
  src: '../public/fonts/NotoSansSC-subset.woff2', // Path to your subsetted font
  display: 'swap', // Recommended for performance
  // You can add weight/style configurations if you have multiple files
  // src: [
  //   {
  //     path: '../public/fonts/NotoSansSC-Regular-subset.woff2',
  //     weight: '400',
  //     style: 'normal',
  //   },
  //   {
  //     path: '../public/fonts/NotoSansSC-Bold-subset.woff2',
  //     weight: '700',
  //     style: 'normal',
  //   },
  // ],
  variable: '--font-noto-sans-sc' // Optional: for use with CSS variables
});

// In your Root Layout (e.g., app/layout.js)
export default function RootLayout({ children }) {
  return (
    <html lang="zh-CN" className={notoSansSC.className}>
      <body>{children}</body>
    </html>
  );
}
```

### Alternative: Using System Fonts (The Easy Way)

If the above process is too complex or your site has a lot of dynamic, user-generated content (where you can't predict the characters), the simplest solution is to rely on the high-quality system fonts already installed on users' devices.

This requires **no downloads** and is instantly fast, but you lose control over the exact look.

```css
/* In your global.css */
:root {
  --font-sans: -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif;
}

body {
  font-family: var(--font-sans);
}
```
This `font-family` stack provides excellent Chinese font fallbacks for macOS, iOS, Windows, and Android.