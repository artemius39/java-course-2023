package edu.project3;

public class AdocRenderer extends BaseRenderer {
    @Override
    protected TableBuilder tableBuilder(StringBuilder sb, int columnCount, String caption, String... columnNames) {
        return new AdocTableBuilder(sb, columnCount, caption, columnNames);
    }

    @Override
    protected String code(String string) {
        return "`" + string + "`";
    }

    private static class AdocTableBuilder implements TableBuilder {
        private static final String TABLE_BOUND = "|===\n";
        private final StringBuilder sb;
        private final int columnCount;

        AdocTableBuilder(StringBuilder sb, int columnCount, String caption, String... columnNames) {
            this.sb = sb;
            this.columnCount = columnCount;

            sb.append("== ")
                    .append(caption)
                    .append("\n\n")
                    .append("[cols=\"")
                    .append("^,".repeat(columnCount))
                    .deleteCharAt(sb.length() - 1)
                    .append("\", options=\"header\"]\n")
                    .append(TABLE_BOUND);
            addRow((Object[]) columnNames);
        }

        @Override
        public void build() {
            sb.append(TABLE_BOUND);
        }

        @Override
        public TableBuilder addRow(Object... cells) {
            if (cells.length != columnCount) {
                throw new IllegalArgumentException("expected " + columnCount + " cells");
            }

            for (Object cell : cells) {
                sb.append('|').append(cell);
            }
            sb.append('\n');

            return this;
        }
    }
}
