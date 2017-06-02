Java.perform(function () {
    var MainActivity = Java.use("com.github.piasy.fridademo.MainActivity");
    MainActivity.private_func.overload("java.lang.String", "boolean").implementation = function (s, b) {
        send("private_func(String,boolean): " + s + ", " + b);
        this.private_func("HOOKED!")
    };
});
