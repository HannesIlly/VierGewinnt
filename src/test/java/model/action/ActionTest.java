package model.action;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {

    private static final File file = new File("test_file");
    private static InputStream inputStream;
    private static OutputStream outputStream;

    /**
     * Create a new empty file before each test.
     */
    @BeforeEach
    public void beforeEach() {
        if (file.exists()) {
            assertTrue(file.delete());
        }
        assertDoesNotThrow(file::createNewFile);
        assertDoesNotThrow(() -> inputStream = new FileInputStream(file));
        assertDoesNotThrow(() -> outputStream = new FileOutputStream(file));
    }

    /**
     * Delete the file after each test.
     */
    @AfterEach
    public void afterEach() {
        assertTrue(file.delete());
    }



    // Start and stop tests of ActionInputDecoder

    /**
     * Tests if the input thread can be started correctly.
     */
    @Test
    public void startInputTest1() {
        ActionInputDecoder in = new ActionInputDecoder(inputStream);

        new Thread(in).start();
        assertTrue(in.isActive());

        in.close();
    }

    /**
     * Tests if the input thread can be stopped.
     */
    @Test
    public void stopInputTest1() {
        ActionInputDecoder in = new ActionInputDecoder(inputStream);
        new Thread(in).start();

        in.close();
        assertFalse(in.isActive());
    }

    /**
     * Tests if the input thread can be stopped after reading some (empty) actions.
     */
    @Test
    public void stopInputTest2() {
        ActionInputDecoder in = new ActionInputDecoder(inputStream);
        new Thread(in).start();

        in.getAction();
        in.getAction();

        in.close();
        assertFalse(in.isActive());
    }



    // Constructor tests of the actions

    @Test
    public void exitActionTest() {
        ExitAction action = new ExitAction("test");
        assertEquals(ActionType.exit, action.getType());
        assertEquals("test", action.getName());
        assertEquals(ExitAction.PLAYER_EXIT, action.getExitType());

        action = new ExitAction("player", ExitAction.SERVER_CLOSED);
        assertEquals(ActionType.exit, action.getType());
        assertEquals("player", action.getName());
        assertEquals(ExitAction.SERVER_CLOSED, action.getExitType());
    }

    @Test
    public void messageActionTest() {
        MessageAction action = new MessageAction("myTestSource", "myTestDestination", "myTestMessage");
        assertEquals(ActionType.message, action.getType());
        assertEquals("myTestSource", action.getSource());
        assertEquals("myTestDestination", action.getDestination());
        assertEquals("myTestMessage", action.getMessage());
    }

    @Test
    public void newGameActionTest() {
        NewGameAction action = new NewGameAction();
        assertEquals(ActionType.newGame, action.getType());
    }

    @Test
    public void newPlayerActionTest() {
        NewPlayerAction action = new NewPlayerAction("testName");
        assertEquals(ActionType.newPlayer, action.getType());
        assertEquals("testName", action.getName());
    }

    @Test
    public void putActionTest() {
        PutAction action = new PutAction(5, 7);
        assertEquals(ActionType.put, action.getType());
        assertEquals(5, action.getColumn());
        assertEquals(7, action.getPiece());
    }



    // Test equals-method in actions (for encoding/decoding tests).

    @Test
    public void equalsExitActionTest1() {
        ExitAction a1 = new ExitAction("TEST_NAME", ExitAction.SERVER_CLOSED);
        ExitAction a2 = new ExitAction("TEST_NAME", ExitAction.SERVER_CLOSED);
        assertEquals(a1, a2);

        a1 = new ExitAction("name-test", ExitAction.PLAYER_EXIT);
        a2 = new ExitAction("name-test", ExitAction.PLAYER_EXIT);
        assertEquals(a1, a2);
    }

    @Test
    public void equalsExitActionTest2() {
        ExitAction a1 = new ExitAction("TEST_NAME", ExitAction.SERVER_CLOSED);
        ExitAction a2 = new ExitAction("TEST_NAME", ExitAction.PLAYER_EXIT);
        assertNotEquals(a1, a2);

        a1 = new ExitAction("testName", ExitAction.PLAYER_EXIT);
        a2 = new ExitAction("TEST_NAME", ExitAction.PLAYER_EXIT);
        assertNotEquals(a1, a2);
    }

    @Test
    public void equalsMessageActionTest1() {
        MessageAction a1 = new MessageAction("srcTest", "destTest", "msgTest");
        MessageAction a2 = new MessageAction("srcTest", "destTest", "msgTest");
        assertEquals(a1, a2);
    }

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

    @Test
    public void equalsNewGameActionTest1() {
        NewGameAction a1 = new NewGameAction();
        NewGameAction a2 = new NewGameAction();
        assertEquals(a1, a2);
    }

    @Test
    public void equalsNewPlayerActionTest1() {
        NewPlayerAction a1 = new NewPlayerAction("testName");
        NewPlayerAction a2 = new NewPlayerAction("testName");
        assertEquals(a1, a2);
    }

    @Test
    public void equalsNewPlayerActionTest2() {
        NewPlayerAction a1 = new NewPlayerAction("testName");
        NewPlayerAction a2 = new NewPlayerAction("nameTest");
        assertNotEquals(a1, a2);
    }

    @Test
    public void equalsPutActionTest1() {
        PutAction a1 = new PutAction(4, 3);
        PutAction a2 = new PutAction(4, 3);
        assertEquals(a1, a2);

        a1 = new PutAction(2, 1);
        a2 = new PutAction(2, 1);
        assertEquals(a1, a2);
    }

    @Test
    public void equalsPutActionTest2() {
        PutAction a1 = new PutAction(1, 3);
        PutAction a2 = new PutAction(7, 3);
        assertNotEquals(a1, a2);

        a1 = new PutAction(2, 1);
        a2 = new PutAction(2, 5);
        assertNotEquals(a1, a2);
    }



    // Test encoding and decoding of actions

    @Test
    public void getActionTest1() {
        ActionOutputEncoder out = new ActionOutputEncoder(outputStream);
        ActionInputDecoder in = new ActionInputDecoder(inputStream);
        new Thread(in).start();

        assertNull(in.getAction());
        assertNull(in.getAction());
        assertNull(in.getAction());
        assertNull(in.getAction());
    }

    @Test
    public void getActionTest2() {
        ActionOutputEncoder out = new ActionOutputEncoder(outputStream);
        ActionInputDecoder in = new ActionInputDecoder(inputStream);
        new Thread(in).start();

        // TODO write working test or rewrite code
        try {
            outputStream.write(5);
            outputStream.close();
            assertEquals(5, inputStream.read());
        } catch (IOException e) {
            fail();
            e.printStackTrace();
        }


        /*
        ExitAction a1 = new ExitAction("TestName", ExitAction.SERVER_CLOSED);
        out.send(a1);
        out.close();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Action a2 = in.getAction();

        assertEquals(a1, a2);*/
    }

    @Test
    public void getActionTest3() {

    }

    @Test
    public void getActionTest4() {

    }

    @Test
    public void getActionTest5() {

    }

    @Test
    public void getActionTest6() {

    }

    @Test
    public void getActionTest7() {

    }
}
