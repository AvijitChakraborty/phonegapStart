
cordova.define("cordova/plugin/downloader",
  function(require, exports, module) {
    var exec = require("cordova/exec");
    var Downloader = function () {};

Downloader.prototype.downloadFile = function(fileUrl, dirName, fileName, overwrite, win, fail) {
	navigator.notification.alert('url: '+fileUrl+' to dir: '+dirName + ' to file: '+fileName);
	if (overwrite == false)
		overwrite = "false";
	else
		overwrite = "true";
	exec(win, fail, "Downloader", "downloadFile", [ fileUrl, dirName, fileName, overwrite ]);

};
var downloader = new Downloader();
    module.exports = downloader;

});
if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.downloader) {
    window.plugins.downloader = cordova.require("cordova/plugin/downloader");
}
