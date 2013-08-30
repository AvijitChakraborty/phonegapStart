$mkdir ~/Hello-World
# Creates a directory for your project called "Hello-World" in your user directory

$cd ~/Hello-World
# Changes the current working directory to your newly created directory

$git init
# Sets up the necessary Git files
# Initialized empty Git repository in /Users/you/Hello-World/.git/

$touch README
# Creates a file called "README" in your Hello-World directory


**FileOpener plugin for Phonegap**

The fileOpener plugin lets you open image, vedio, pdf, audio excel file through application

Adding the Plugin to your project

Using this plugin requires Android PhoneGap.

To install the plugin, move file.js to your project's www folder and include a reference to it in your html file after phonegap.js.

    <script type="text/javascript" charset="utf-8" src="cordova.js"></script>
    <script type="text/javascript" charset="utf-8" src="file.js"></script>

Create a directory within your project called "src/com/phonegap/plugins/fileOpener" and move FileOpener.java into it.

Using the plugin

The plugin creates the object window.plugins.fileOpener. To use, call one of the following, available methods:

    window.plugins.fileOpener.open(url);
   
Sample use:

    window.plugins.fileOpener.open("http://abc.com");


RELEASE NOTES

**Aug 30, 2013**

Initial release
BUGS AND CONTRIBUTIONS
**
The MIT License**

Copyright (c) Avijit Chakraborty

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.