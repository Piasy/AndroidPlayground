Java.perform(function () {
    var Activity = Java.use("android.app.Activity");
    Activity.onResume.implementation = function () {
        send("onResume() called " + this);
        this.onResume();
    };
});
