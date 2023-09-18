package org.dbnoobs.noobdb.repl;

import org.dbnoobs.noobdb.memorybackend.*;
import org.dbnoobs.noobdb.parser.Parser;
import org.dbnoobs.noobdb.parser.ast.AST;
import org.dbnoobs.noobdb.parser.ast.ASTType;
import org.dbnoobs.noobdb.parser.ast.Statement;
import org.dbnoobs.noobdb.repl.exceptions.ExitException;

import java.util.List;
import java.util.Scanner;

public class REPL {
    private static final String BANNER = " __      __   _  ___ ___  __  __ ___   _____ ___   \n" +
            " \\ \\    / /__| |/ __/ _ \\|  \\/  | __| |_   _/ _ \\  \n" +
            "  \\ \\/\\/ / -_) | (_| (_) | |\\/| | _|    | || (_) | \n" +
            "   \\_/\\_/\\___|_|\\___\\___/|_|  |_|___|   |_| \\___/  \n" +
            "                                                   \n" +
            "  _  _  ___   ___  ___ ___  ___                    \n" +
            " | \\| |/ _ \\ / _ \\| _ )   \\| _ )                   \n" +
            " | .` | (_) | (_) | _ \\ |) | _ \\                   \n" +
            " |_|\\_|\\___/ \\___/|___/___/|___/                   \n" +
            "                                                   \n";

    private static final String VERSION = "0.0.1";

    public static final String STARTER_MESSAGE = "This is a noob db which only knows how to process single line statements\nTo terminate the program enter .exit\n";
    public static void main(String[] args) {
        runREPL();
    }

    private static void runREPL() {
        System.out.print(BANNER);
        System.out.println("Version: " + VERSION);
        System.out.print(STARTER_MESSAGE);
        Scanner keyboard = new Scanner(System.in);
        MemoryBackend memoryBackend = new MemoryBackend();
        Parser parser = new Parser();
        while(true){
            System.out.print("> ");
            String command = keyboard.nextLine();
            if(command == null || command.isEmpty() || command.isBlank()) {
                // do nothing
            } else if(command.startsWith(".")){
                try {
                    process_meta_command(command);
                }catch (ExitException e){
                    System.out.println("Exiting..");
                    System.out.println("Bye!");
                    break;
                }
            }else{
                try{
                    AST ast = parser.parse(command);
                    if(ast == null || ast.getStatements() == null){
                        System.out.println("please give valid sql");
                        throw new RuntimeException();
                    }
                    for(Statement statement: ast.getStatements()){
                        if(statement.getType().equals(ASTType.SELECT)){
                            Results results = memoryBackend.select(statement.getSelectStatement());
                            if(results == null || results.getColumns() == null || results.getColumns().size() == 0|| results.getRows() == null || results.getRows().size()==0){
                                System.out.println("Table is empty");
                            }else{
                                System.out.print("|");
                                for(Column column: results.getColumns()){
                                    System.out.print(column.getName() + " |");
                                }
                                System.out.print("\n");
                                for(int i=0;i<results.getColumns().size();i++){
                                    System.out.print("--------------");
                                }
                                System.out.print("\n");
                                for(List<Cell> row: results.getRows()){
                                    System.out.print("|");
                                    for(int i=0;i<row.size();i++){
                                        if(results.getColumns().get(i).getType().equals(ColumnType.INT)){
                                            System.out.print(row.get(i).asInt() + " |");
                                        }else{
                                            System.out.print(row.get(i).asText() + " |");
                                        }

                                    }
                                    System.out.print("\n");
                                }
                            }
                        }else if(statement.getType().equals(ASTType.CREATE)){
                            memoryBackend.createTable(statement.getCreateTableStatement());
                            System.out.println("Create table succeeded");
                        }else if(statement.getType().equals(ASTType.INSERT)){
                            memoryBackend.insert(statement.getInsertStatement());
                            System.out.println("values inserted");
                        }else{
                            System.out.println("Expected a select, create or insert statement but not found");
                            throw new RuntimeException();
                        }
                    }
                }catch (Exception e){
                    System.out.println("Encountered unexpected error please try again");
                }


            }
        }
    }

    private static void process_meta_command(String command) {
        if(".exit".equals(command)){
            throw new ExitException();
        }else{
            System.out.println("Unrecognised meta command: " + command);
        }
    }
}
