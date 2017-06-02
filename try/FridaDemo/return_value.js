function print_args() {
    var str = "";
    for (var i = 0; i < arguments.length; i++) {
        str += arguments[i] + ", "
    }
    return str;
}

Java.perform(function () {
    var MainActivity = Java.use("com.github.piasy.fridademo.MainActivity");
    MainActivity.func_with_ret.implementation = function(i) {
        send("func_with_ret(int): " + i);
        return 100;
    };

    var AudioRecord = Java.use("android.media.AudioRecord");
    AudioRecord.getMinBufferSize.implementation = function(sampleRateInHz, channelConfig, audioFormat) {
        var real = this.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat)
        send("getMinBufferSize: " + print_args(sampleRateInHz, channelConfig, audioFormat) + "real ret: " + real);
        return -1;
    };
});
