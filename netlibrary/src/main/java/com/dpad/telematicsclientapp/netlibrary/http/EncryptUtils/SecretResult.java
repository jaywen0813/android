package com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-06-03-0003 13:48
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SecretResult implements Serializable {

    /**
     * code : 00000
     * message : 成功
     * result : {"ak":"XDm0gN0XFPNC0SaTGWBCJsDUhuLI6svIfFIx/E17ddGlQkVlLHkZi0IcP19eE7Gx4LJQpUEouLq/NBoZosYuv6NeW7wsTJ+tcrezEUEL1X08m47RvH1Wtzaufuju/j8OoL2wM4IDMhkhONSyhZYhCYaEw2Hpn0VTSMU8ezHCt7+UYqJH/5DBk6xjuW+gLRbFeffn73oml13qMikJeL4z8Mg0cu0CflWEVNNV2pivD8D5M11NbaP4NcWG5N5l4kK4OiAun9O6Rn/I7dyerwFs4rTgkXYqT0EnvzKGmIcZIiaXw3bR5WRGBerta4Oqf08/L4t8KIDlnZoVIfPEXPE1pw==","ct":"Ed3gAB5QIG8iopaRcTS7DbOgmVijk6sS1IGK3mMmURdp8hslWSwMfiWF8MJn9Jx+YbbKwg7kMbJMCHTymMk4Rc1RE21WrfYlNpLnQW9utFA9m4f0Kgzf9wjYXzxiqrePHHuROn/5r5GFSd+9Rj5Oct0ix0h1dSPo0QQ34qAjrLZKHFm9F5AuklyxrW/FVJf1ExlnqqQ9TGyFrMmsX6U5feniGZ0SklVCN0nx37DJjrOAUUoDbCqiRMrTqT4Okw9g4yyRxkHD00TCYEJC5pAN/zF25VR7scUbQVu5+e8ZzRRVCaEAHRnTYgiEpr0yXRQjI4B6dg1iYjzrvqwrKBa2iNbcOQi2Dgx/TdUTQdt94SGVwQGKMLCihP6wnth7LmHsyYAdfcfhvLE60cGt5vhpCKtasnqP2jU7grfpwrrL1IrFEK4ToYYooYSM6ed7UOJNJezPSK4BJN5i7DF0Eb8185u+qlhzzMMKswkAyFiduZksaGcbmTjIzqw/C6kHCOvZf6p8QzBp2RpGkCFDdqxKag1fK1DIVpGc+rQklGPN0o7Fk8pim3rzU/frBcjJKCnymqhxt3SVvWO10H0pzNjkZqne+MkohgCHOLrpJYTRsWW0ttrr0UhSj1XqdM5tiLJKFM800NxN49wr9DVwmIl1FHrojIXdWAxOlfRd/Hvg4eGPodEr9zAiEEKPzEtuz7OXgDry8x9mbDeW6SXmv8/FnvZGbgjdFR2m5bLgPPWnv/k="}
     * total : null
     */

    private String code;
    private String message;
    private String result;
    private String total;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
