package com.github.piasy.fancytransitiondemo;

import android.widget.ImageView;

/**
 * Created by Piasy{github.com/Piasy} on 15/11/26.
 */
public interface ScreenTransferController {
    void showInvitingDialog(String fromUserAvatar, String fromUsername, String toUserAvatar,
            String toUsername, long toUid);

    void showInvitedDialog(String fromUserAvatar, String fromUsername, String toUserAvatar,
            String toUsername, long toUid);

    void showTransferringFragment(String fromUserAvatar, String fromUsername, String toUserAvatar,
            String toUsername, long uid, ImageView imageView);

    void showCountdownFragment4NewViewers(String toUserAvatar, String toUsername, ImageView imageView);

    void showCountdownFragment4NewPublisher();

    void screenTransferSuccess();

    void quitScreenTransferAtTransfer();

    void quitScreenTransferAtCountdown();
}
