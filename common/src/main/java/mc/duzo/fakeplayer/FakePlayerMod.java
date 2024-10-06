package mc.duzo.fakeplayer;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import mc.duzo.fakeplayer.client.FakePlayerModClient;
import mc.duzo.fakeplayer.network.FPNetwork;

public class FakePlayerMod {
    public static final String MOD_ID = "fakeplayer";

    public static void init() {
        Register.init();
        FPNetwork.init();

        if (Platform.getEnvironment() == Env.CLIENT) {
            FakePlayerModClient.init();
        }
    }
}
