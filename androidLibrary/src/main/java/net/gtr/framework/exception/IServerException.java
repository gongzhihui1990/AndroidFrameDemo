package net.gtr.framework.exception;

/**
 *
 * @author caroline
 * @date 2018/5/7
 */

public class IServerException extends IException {

    private String errCode ="";
    public IServerException(String msg ) {
        super(msg);
    }
    public IServerException(String msg ,String errCode) {
        super(msg);
        this.errCode =errCode;
    }

    public String getErrCode() {
         return errCode;
    }
}
