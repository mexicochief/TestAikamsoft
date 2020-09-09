package org.kolesnikov.resolver;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.kolesnikov.resolver.factory.ExecutorFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class NodeResolver {
    private final List<ExecutorFactory> executorFactorys;
    private final Logger logger;
    private final Gson gson;

    public NodeResolver(List<ExecutorFactory> executorFactoryList, Logger logger, Gson gson) {
        this.executorFactorys = executorFactoryList;
        this.logger = logger;
        this.gson = gson;
    }

    public ExecutorFactory resolve(List<String> names) {
        final Optional<ExecutorFactory> optionalExecutorFactory = executorFactorys
                .stream()
                .filter(executorFactory -> executorFactory.hasSameType(names))
                .findFirst();
        if (optionalExecutorFactory.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            names.forEach(name -> stringBuilder.append(name).append(" "));
            final String errorMessage = "incorrect properties: " + stringBuilder;
            logger.error(gson.toJson(Map.of("error", errorMessage)));
            throw new RuntimeException(errorMessage);
        }
        return optionalExecutorFactory.get();
    }
}
