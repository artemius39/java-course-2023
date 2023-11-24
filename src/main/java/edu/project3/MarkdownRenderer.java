package edu.project3;

public class MarkdownRenderer extends BaseRenderer {
    @Override
    protected TableBuilder tableBuilder(StringBuilder sb, int columnCount, String caption, String... columnNames) {
        return new MarkdownTableBuilder(sb, columnCount, caption, columnNames);
    }

    @Override
    protected String code(String string) {
        return "`" + string + "`";
    }

    private static class MarkdownTableBuilder implements TableBuilder {
        private final StringBuilder sb;
        private final int columnCount;

        MarkdownTableBuilder(StringBuilder sb, int columnCount, String caption, String... columnNames) {
            this.sb = sb;
            this.columnCount = columnCount;

            sb.append("#### ")
                    .append(caption)
                    .append("\n\n");

            addRow((Object[]) columnNames);

            sb.append("|")
                    .append(":-:|".repeat(columnCount))
                    .append('\n');
        }

        @Override
        public void build() {
        }

        @Override
        public TableBuilder addRow(Object... cells) {
            if (cells.length != columnCount) {
                throw new IllegalArgumentException("expected " + columnCount + " cells");
            }

            sb.append('|');
            for (Object cell : cells) {
                sb.append(cell).append('|');
            }
            sb.append('\n');

            return this;
        }
    }
}
