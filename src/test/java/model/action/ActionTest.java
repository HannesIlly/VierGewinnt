package model.action;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {

    private static final int TIMEOUT = 1000;

    private static final File file = new File("test_file");

    private static InputStream inputStream;
    private static OutputStream outputStream;

    // helper methods

    /**
     * Reads an action from the given {@link ActionInputDecoder}. The {@link ActionInputDecoder} thread has to be already started.
     * If the action could not be read within the given timeout a {@link java.util.concurrent.TimeoutException} will be thrown.
     *
     * @param in      The {@link ActionInputDecoder} from which is read.
     * @param timeout The timeout within the action should be read.
     * @return The action that was read.
     * @throws TimeoutException If the action could not be read within the timeout.
     */
    public Action readActionTimeout(ActionInputDecoder in, int timeout) throws TimeoutException {
        Action inputAction = null;
        long startTime = System.currentTimeMillis();
        while (inputAction == null) {
            inputAction = in.getAction();
            if (System.currentTimeMillis() - startTime >= timeout) {
                throw new TimeoutException();
            }
        }
        return inputAction;
    }


    // before and after methods

    /**
     * Create a new empty file before each test.
     */
    @BeforeEach
    public void beforeEach() {
        // reset input/output file
        if (file.exists()) {
            assertTrue(file.delete());
        }
        assertDoesNotThrow(file::createNewFile);

        // reset input/output streams
        try {
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            fail("The input stream could not be created.");
        }
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


    // Test encoding and decoding of actions

    /**
     * Tests sending and reading data via {@link java.io.FileInputStream} and {@link java.io.FileOutputStream}.
     * Used to see if the other tests can work correctly.
     */
    @Test
    public void ioTest1() {
        try {
            outputStream.write(1);

            assertEquals(1, inputStream.read());

            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            fail("File could not be read.");
        }
    }

    /**
     * Tests reading actions when there are no actions available to be read.
     */
    @Test
    public void getActionTest1() {
        ActionInputDecoder in = new ActionInputDecoder(inputStream);
        new Thread(in).start();

        assertNull(in.getAction());
        assertNull(in.getAction());
        assertNull(in.getAction());
        assertNull(in.getAction());
    }

    /**
     * Tests sending and receiving a NewGameAction.
     */
    @Test
    public void getActionTest2() {
        try {
            ActionOutputEncoder out = new ActionOutputEncoder(outputStream);
            ActionInputDecoder in = new ActionInputDecoder(inputStream);
            new Thread(in).start();

            // send action (empty NewGameAction)
            Action outputAction = new NewGameAction();
            out.send(outputAction);
            // read action
            Action inputAction = readActionTimeout(in, TIMEOUT);
            // compare
            assertEquals(outputAction, inputAction);

            in.close();
            out.close();
        } catch (TimeoutException e) {
            fail("The action could not be read within the given timeout.");
        }
    }

    /**
     * Tests sending and receiving a {@link model.action.ExitAction}.
     */
    @Test
    public void getActionTest3() {
        try {
            ActionOutputEncoder out = new ActionOutputEncoder(outputStream);
            ActionInputDecoder in = new ActionInputDecoder(inputStream);
            new Thread(in).start();

            // send action
            Action outputAction = new ExitAction("TEST");
            out.send(outputAction);
            // read action
            Action inputAction = readActionTimeout(in, TIMEOUT);
            // compare TODO continue here
            // TODO Exit/NewPlayer
            assertEquals(outputAction, inputAction);

            in.close();
            out.close();
        } catch (TimeoutException e) {
            fail("The action could not be read within the given timeout.");
        }
    }

    /**
     * Tests sending and receiving a {@link model.action.MessageAction}.
     */
    @Test
    public void getActionTest4() {
        try {
            ActionOutputEncoder out = new ActionOutputEncoder(outputStream);
            ActionInputDecoder in = new ActionInputDecoder(inputStream);
            new Thread(in).start();

            // send action
            Action outputAction = new MessageAction("testSrc", "testDest", "TEST");
            out.send(outputAction);
            // read action
            Action inputAction = readActionTimeout(in, TIMEOUT);
            // compare
            assertEquals(outputAction, inputAction);
            // TODO Message/NewPlayer

            in.close();
            out.close();
        } catch (TimeoutException e) {
            fail("The action could not be read within the given timeout.");
        }
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
