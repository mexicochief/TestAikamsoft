package org.kolesnikov.resolver;

import java.util.List;

public class NodeResolver {
    private final List<ExecutorFactory> executorFactorys;


    public NodeResolver(List<ExecutorFactory> executorFactorys) {
        this.executorFactorys = executorFactorys;
    }

    public ExecutorFactory resolve(List<String> names) {
        return executorFactorys.stream().filter(executorFactory -> executorFactory.hasSameType(names)).findFirst().orElse(null); //todo
    }
}
