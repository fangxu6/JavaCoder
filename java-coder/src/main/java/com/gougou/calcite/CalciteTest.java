package com.gougou.calcite;

import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlFunction;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlBasicVisitor;

import java.util.ArrayList;
import java.util.List;

public class CalciteTest {

    public static void main(String[] args) throws SqlParseException {
        String sql = "select concat('test-', upper(name)) from test limit 3";
        SqlParser parser = SqlParser.create(sql);
        SqlNode stmt = parser.parseStmt();
        FunctionExtractor functionExtractor = new FunctionExtractor();
        stmt.accept(functionExtractor);
        // [CONCAT, UPPER]
        System.out.println(functionExtractor.getFunctions());
    }

    private static class FunctionExtractor extends SqlBasicVisitor<Void> {

        private final List<String> functions = new ArrayList<>();

        @Override
        public Void visit(SqlCall call) {
            if (call.getOperator() instanceof SqlFunction) {
                functions.add(call.getOperator().getName());
            }
            return super.visit(call);
        }

        public List<String> getFunctions() {
            return functions;
        }
    }
}