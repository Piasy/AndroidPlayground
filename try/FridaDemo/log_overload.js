Java.perform(function () {
    var MainActivity = Java.use("com.github.piasy.fridademo.MainActivity");
    MainActivity.private_func.overload().implementation = function () {
        send("private_func()");
        this.private_func();
    };
    MainActivity.private_func.overload("int").implementation = function (i) {
        send("private_func(int): " + i);
        this.private_func(i);
    };
    MainActivity.private_func.overload("java.lang.String").implementation = function () {
        send("private_func(String): " + arguments[0]);
        this.private_func(arguments[0]);
    };
    MainActivity.private_func.overload("java.lang.String", "boolean").implementation = function (s, b) {
        send("private_func(String,boolean): " + s + ", " + b);
        this.private_func(s, b);
    };
});
