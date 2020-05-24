package model.action;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class ActionEncodingDecodingTest {

    private static final int TIMEOUT = 1000;

    private static final File file = new File("test_file");

    /*
     * The input stream should only be used, when all contents were written to the file by the output stream.
     */
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

            // send action (empty NewGameAction)
            Action outputAction = new NewGameAction();
            out.send(outputAction);
            // read action
            new Thread(in).start();
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

            // send action
            Action outputAction = new ExitAction("TEST");
            out.send(outputAction);
            // read action
            new Thread(in).start();
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
     * Tests sending and receiving a {@link model.action.MessageAction}.
     */
    @Test
    public void getActionTest4() {
        try {
            ActionOutputEncoder out = new ActionOutputEncoder(outputStream);
            ActionInputDecoder in = new ActionInputDecoder(inputStream);

            // send action
            Action outputAction = new MessageAction("testSrc", "testDest", "TEST");
            out.send(outputAction);
            // read action
            new Thread(in).start();
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
     * Tests sending and receiving a {@link model.action.NewPlayerAction}.
     */
    @Test
    public void getActionTest5() {
        try {
            ActionOutputEncoder out = new ActionOutputEncoder(outputStream);
            ActionInputDecoder in = new ActionInputDecoder(inputStream);

            // send action
            Action outputAction = new NewPlayerAction("NewPlayEr");
            out.send(outputAction);
            // read action
            new Thread(in).start();
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
     * Tests sending and receiving a {@link model.action.PutAction}.
     */
    @Test
    public void getActionTest6() {
        try {
            ActionOutputEncoder out = new ActionOutputEncoder(outputStream);
            ActionInputDecoder in = new ActionInputDecoder(inputStream);

            // send action
            Action outputAction = new PutAction(5, 9);
            out.send(outputAction);
            // read action
            new Thread(in).start();
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
     * Tests sending multiple different actions and reading them.
     */
    @Test
    public void getActionTest7() {
        try {
            ActionOutputEncoder out = new ActionOutputEncoder(outputStream);
            ActionInputDecoder in = new ActionInputDecoder(inputStream);

            List<Action> actions = new LinkedList<>();
            actions.add(new NewPlayerAction("TESTname"));
            actions.add(new NewPlayerAction("SecondPlayer"));
            actions.add(new PutAction(5, 1));
            actions.add(new MessageAction("1", "2", "You will never win!"));
            actions.add(new PutAction(3, 2));
            actions.add(new PutAction(3, 1));
            actions.add(new ExitAction("SecondPlayer", ExitAction.PLAYER_EXIT));

            // send actions
            for (Action a : actions) {
                out.send(a);
            }
            // read actions
            new Thread(in).start();
            List<Action> inputActions = new LinkedList<>();
            for (int i = 0; i < actions.size(); i++) {
                inputActions.add(readActionTimeout(in, TIMEOUT));
            }
            // compare
            assertIterableEquals(actions, inputActions);

            in.close();
            out.close();
        } catch (TimeoutException e) {
            fail("The action could not be read within the given timeout.");
        }
    }

}
