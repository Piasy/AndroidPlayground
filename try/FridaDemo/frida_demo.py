import frida, sys

package_name = "com.github.piasy.fridademo"

def get_messages_from_js(message, data):
    print(message)


def hook_log_on_resume():
    hook_code = """
    Java.perform(function () {
        var Activity = Java.use("android.app.Activity");
        Activity.onResume.implementation = function () {
            send("onResume() " + this);
            this.onResume();
        };
    });
    """
    return hook_code


def main():
    process = frida.get_device_manager().enumerate_devices()[-1].attach(package_name)
    script = process.create_script(hook_log_on_resume())
    script.on('message', get_messages_from_js)
    script.load()
    sys.stdin.read()


if __name__ == '__main__':
    main()
