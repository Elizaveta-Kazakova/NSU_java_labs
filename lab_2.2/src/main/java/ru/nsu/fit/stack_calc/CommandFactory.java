package ru.nsu.fit.stack_calc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.nsu.fit.stack_calc.commands.Command;
import ru.nsu.fit.stack_calc.exceptions.InvalidConfigFile;
import ru.nsu.fit.stack_calc.exceptions.UnknownCommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final int FIRST_EL = 0;
    private final int SECOND_EL = 1;
    private final int NUM_OF_CONFIG_ARGS = 2;
    private File configFile;
    private Map<String, String> configData = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(CommandFactory.class);

    private void downloadConfigFileData() throws IOException, InvalidConfigFile {
        try (BufferedReader configFileReader = new BufferedReader(new FileReader(configFile))) {
            logger.info("Start to download config file");
            String newLine;
            while ((newLine = configFileReader.readLine()) != null) {
                String[] configEls = newLine.split("\\s");
                if (configEls.length != NUM_OF_CONFIG_ARGS) {
                    throw new InvalidConfigFile("Too many elements in string in configure file!" + " It is right to have " + NUM_OF_CONFIG_ARGS + " arguments.");
                }
                configData.put(configEls[FIRST_EL], configEls[SECOND_EL]);
            }
            if (configData.isEmpty()) {
                throw new InvalidConfigFile("Empty configure file!");
            }
            logger.info("Configured file are downloaded");
        } catch(IOException e) {
            throw e;
        }
    }

    public CommandFactory(String configFileName) {
        File configFile = new File(configFileName);
        this.configFile = configFile;
    }

    public Command createCommand(String commandName) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException,
            UnknownCommand, InvalidConfigFile {
        if (configData.isEmpty()) {
            downloadConfigFileData();
        }
        if (!configData.containsKey(commandName)) {
            throw new UnknownCommand("Command " + commandName + " not configured!");
        }
        logger.info("Start to create command {}", commandName );
        String commandPath = configData.get(commandName);
        Class<?> curClass = Class.forName(commandPath); // example commandPath = ru.nsu.fit.stack_calc.commands.Push
        Command command = (Command) curClass.getDeclaredConstructor().newInstance();
        logger.info("Command {} are created", commandName );
        return command;
    }

}
