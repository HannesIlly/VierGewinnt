package control;

public class Main {
    /*
    public static void main(String[] args) {
        String name = "";
        boolean createServer = false;
        String input = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {
            System.out.println("Fehler bei erstellung der IP-Adresse.");
            System.exit(0);
            e1.printStackTrace();
        }
        
        // enter the user's name
        name = inputName(in);
        
        // enter if a new server has to be created
        System.out.println("Möchtest du ein neues Spiel erstellen?");
        System.out.println("(ja/nein eingeben)");
        System.out.println("Bei \"nein\" wird einem existierenden Spiel beigetreten.");
        while (input != null) {
            try {
                input = in.readLine();
            } catch (IOException e) {
                System.out.println("Fehler bei der Eingabe: bitte noch einmal!");
                e.printStackTrace();
                continue;
            }
            if (input != "") {
                switch (input.toLowerCase()) {
                case "ja":
                case "yes":
                case "true":
                case "y":
                case "j":
                    createServer = true;
                    try {
                        System.out.println("Deine IP-Adresse ist: " + InetAddress.getLocalHost());
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    input = null;
                    break;
                case "nein":
                case "no":
                case "false":
                case "n":
                    createServer = false;
                    input = null;
                    break;
                default:
                    System.out.println("Falsche Eingabe! Bitte erneut wählen.");
                    break;
                }
            }
        }
        
        if (createServer) {
            System.out.println("Server wird erstellt");
            try {
                Server server = new Server();
                new Thread(server).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Bitte IP-Adresse eingeben:");
            input = "";
            while (input != null) {
                try {
                    input = in.readLine();
                } catch (IOException e) {
                    System.out.println("Fehler bei der Eingabe: bitte noch einmal!");
                    e.printStackTrace();
                    continue;
                }
                if (input != "") {
                    try {
                        ipAddress = InetAddress.getByName(input);
                        input = null;
                    } catch (UnknownHostException e) {
                        System.out.println("Format der IP-Adresse ungültig.");
                        e.printStackTrace();
                    }
                }
            }
        }
        
        System.out.println("Spiel wird beigetreten");
        try {
            Client client = new Client(ipAddress, name, e -> System.out.println(""));
            new Thread(client).start();
        } catch (UnknownHostException e) {
            System.out.println("Der Host " + ipAddress + " ist leider nicht korrekt.");
            // e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Ask the user to enter a name.
     * 
     * @return the name
     *//*
    private static String inputName(BufferedReader in) {
        String name = "";
        System.out.println("Bitte Spielernamen eingeben: ");
        while (true) {
            try {
                name = in.readLine();
            } catch (IOException e) {
                System.out.println("Fehler bei der Eingabe: bitte noch einmal!");
                e.printStackTrace();
                continue;
            }
            if (name != null && !name.equals("")) {
                return name; // if the name is not empty it is returned
            }
        }
    }
    */
}
