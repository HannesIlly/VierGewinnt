package model.action;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ActionTest {

    // Constructor tests of the actions

    /**
     * Tests the constructor of {@link ExitAction}.
     */
    @Test
    public void exitActionTest() {
        ExitAction action = new ExitAction("test");
        assertEquals(Action.TYPE_EXIT, action.getType());
        assertEquals("test", action.getName());
        assertEquals(ExitAction.PLAYER_EXIT, action.getExitType());

        action = new ExitAction("player", ExitAction.SERVER_CLOSED);
        assertEquals(Action.TYPE_EXIT, action.getType());
        assertEquals("player", action.getName());
        assertEquals(ExitAction.SERVER_CLOSED, action.getExitType());
    }

    /**
     * Tests the constructor of {@link MessageAction}.
     */
    @Test
    public void messageActionTest() {
        MessageAction action = new MessageAction("myTestSource", "myTestDestination", "myTestMessage");
        assertEquals(Action.TYPE_MESSAGE, action.getType());
        assertEquals("myTestSource", action.getSource());
        assertEquals("myTestDestination", action.getDestination());
        assertEquals("myTestMessage", action.getMessage());
    }

    /**
     * Tests the constructor of {@link NewGameAction}.
     */
    @Test
    public void newGameActionTest() {
        NewGameAction action = new NewGameAction();
        assertEquals(Action.TYPE_NEW_GAME, action.getType());
    }

    /**
     * Tests the constructor of {@link NewPlayerAction}.
     */
    @Test
    public void newPlayerActionTest() {
        NewPlayerAction action = new NewPlayerAction("testName");
        assertEquals(Action.TYPE_NEW_PLAYER, action.getType());
        assertEquals("testName", action.getName());
    }

    /**
     * Tests the constructor of {@link PutAction}.
     */
    @Test
    public void putActionTest() {
        PutAction action = new PutAction(5, 7);
        assertEquals(Action.TYPE_PUT, action.getType());
        assertEquals(5, action.getColumn());
        assertEquals(7, action.getPiece());
    }


    // Test equals-method in actions (for encoding/decoding tests).

    /**
     * Tests the equals method in {@link ExitAction} with equal objects.
     */
    @Test
    public void equalsExitActionTest1() {
        ExitAction a1 = new ExitAction("TEST_NAME", ExitAction.SERVER_CLOSED);
        ExitAction a2 = new ExitAction("TEST_NAME", ExitAction.SERVER_CLOSED);
        assertEquals(a1, a2);

        a1 = new ExitAction("name-test", ExitAction.PLAYER_EXIT);
        a2 = new ExitAction("name-test", ExitAction.PLAYER_EXIT);
        assertEquals(a1, a2);
    }

    /**
     * Tests the equals method in {@link ExitAction} when it should return {@code false}.
     */
    @Test
    public void equalsExitActionTest2() {
        ExitAction a1 = new ExitAction("TEST_NAME", ExitAction.SERVER_CLOSED);
        ExitAction a2 = new ExitAction("TEST_NAME", ExitAction.PLAYER_EXIT);
        assertNotEquals(a1, a2);

        a1 = new ExitAction("testName", ExitAction.PLAYER_EXIT);
        a2 = new ExitAction("TEST_NAME", ExitAction.PLAYER_EXIT);
        assertNotEquals(a1, a2);
    }

    /**
     * Tests the equals method in {@link MessageAction} with equal objects.
     */
    @Test
    public void equalsMessageActionTest1() {
        MessageAction a1 = new MessageAction("srcTest", "destTest", "msgTest");
        MessageAction a2 = new MessageAction("srcTest", "destTest", "msgTest");
        assertEquals(a1, a2);
    }

    /**
     * Tests the equals method in {@link MessageAction} when it should return {@code false}.
     */
    @Test
    public void equalsMessageActionTest2() {
        MessageAction a1 = new MessageAction("srcTest", "destTest", "msgTest");
        MessageAction a2 = new MessageAction("sourceTest", "destTest", "msgTest");
        assertNotEquals(a1, a2);

        a1 = new MessageAction("srcTest", "destTest", "msgTest");
        a2 = new MessageAction("srcTest", "destinationTest", "msgTest");
        assertNotEquals(a1, a2);

        a1 = new MessageAction("srcTest", "destTest", "msgTest");
        a2 = new MessageAction("srcTest", "destTest", "messageTest");
        assertNotEquals(a1, a2);
    }

    /**
     * Tests the equals method in {@link NewGameAction} with equal objects.
     */
    @Test
    public void equalsNewGameActionTest1() {
        NewGameAction a1 = new NewGameAction();
        NewGameAction a2 = new NewGameAction();
        assertEquals(a1, a2);
    }

    /**
     * Tests the equals method in {@link NewPlayerAction} with equal objects.
     */
    @Test
    public void equalsNewPlayerActionTest1() {
        NewPlayerAction a1 = new NewPlayerAction("testName");
        NewPlayerAction a2 = new NewPlayerAction("testName");
        assertEquals(a1, a2);
    }

    /**
     * Tests the equals method in {@link NewPlayerAction} when it should return {@code false}.
     */
    @Test
    public void equalsNewPlayerActionTest2() {
        NewPlayerAction a1 = new NewPlayerAction("testName");
        NewPlayerAction a2 = new NewPlayerAction("nameTest");
        assertNotEquals(a1, a2);
    }

    /**
     * Tests the equals method in {@link PutAction} with equal objects.
     */
    @Test
    public void equalsPutActionTest1() {
        PutAction a1 = new PutAction(4, 3);
        PutAction a2 = new PutAction(4, 3);
        assertEquals(a1, a2);

        a1 = new PutAction(2, 1);
        a2 = new PutAction(2, 1);
        assertEquals(a1, a2);
    }

    /**
     * Tests the equals method in {@link PutAction} when it should return {@code false}.
     */
    @Test
    public void equalsPutActionTest2() {
        PutAction a1 = new PutAction(1, 3);
        PutAction a2 = new PutAction(7, 3);
        assertNotEquals(a1, a2);

        a1 = new PutAction(2, 1);
        a2 = new PutAction(2, 5);
        assertNotEquals(a1, a2);
    }

}
