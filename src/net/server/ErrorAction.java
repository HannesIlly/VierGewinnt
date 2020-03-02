 package net.server;

import util.action.Action;
import util.action.ActionType;
import util.action.ExitAction;
import util.action.NewGameAction;
import util.action.NewPlayerAction;
import util.action.PutAction;

public class ErrorAction extends Action {
    
    private Action action;
    
    public ErrorAction(Action action) {
        super(ActionType.error);
        if (action.getType() == ActionType.error) {
            throw new IllegalArgumentException("Eine Error-Action kann keine Error-Action beinhalten!");
        }
        this.action = action;
    }
    
    @Override
    public byte[] encode() {
        byte[] actionCode = action.encode();
        byte[] encodedData = new byte[actionCode.length + 2];
        encodedData[0] = (byte) action.getType().ordinal();
        encodedData[1] = (byte) actionCode.length;
        for (int i = 0; i < actionCode.length; i++) {
            encodedData[i + 2] = actionCode[i];
        }
        return encodedData;
    }
    
    /**
     * Decodes this error action and returns the content, which is the action that caused the error.
     * 
     * @param data The action data, that will be decoded.
     * @return The action that caused an error.
     */
    public Action decode(byte[] data) {
        ActionType type = ActionType.values()[data[0]];
        byte[] actionData = new byte[data[1]];
        for (int i = 0; i < actionData.length; i++) {
            actionData[i] = data[i + 2];
        }
        switch (type) {
        case newPlayer:
            return NewPlayerAction.decode(actionData);
        case put:
            return PutAction.decode(actionData);
        case newGame:
            return NewGameAction.decode(actionData);
        case exit:
            return ExitAction.decode(actionData);
        case undo:
        case message:
        case error:
        default:
            System.out.println("Fehler beim umwandeln: ungültiger action type. type = " + type.toString());
            break;
        }
        return null;
    }
    
}
