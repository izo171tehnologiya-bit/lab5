import java.util.Arrays;

class run_code {
    public static void main() {

        char[][] first = {
                {'И', 'м', 'я'},
                {'Ф', 'а', 'м', 'и', 'л', 'и', 'я'},
                {'О', 'т', 'ч', 'е', 'с'}
        };
        char[][] second = {
                {'а', 'а', 'а'},
                {'б', 'б', 'б', 'б', 'б', 'б', 'д'},
                {'в', 'в', 'в'}
        };
        char[][] third = {
                {'г', 'г', 'г'},
                {'д', 'д', 'д', 'д', 'д', 'д', 'д'},
                {'е', 'е', 'е'}
        };
        char[][] fourth = {
                {'ё', 'ё', 'ё'},
                {'ж', 'ж', 'ж', 'ж', 'ж', 'ж', 'ж'},
                {'з', 'з', 'з'}
        };
        char[][] fifth = {
                {'ъ', 'у', 'ъ'},
                {'ы', 'ы', 'ы', 'ы', 'ы', 'ы', 'ы'},
                {'ъ', 'ы', 'ъ'}
        };
        char[][][] list = {first, second, third, fourth, fifth};

        System.out.print("Таблица: \n");
        Node[][] table = create_table();
        table = fill_table(table, list);
        print(table);
        System.out.print("\n\nПоиск 1 элемента по ключу:\n ");
        int fir_hash = Math.abs(hash(first)) % 10;
        System.out.print(table[0][0].find(fir_hash, 0));
        System.out.print("\n\nВставка элемента:\n");
        table[0][0].insert(first);
        print(table);
        System.out.print("\n\nУдаление элемента:\n");
        table[0][0].delite(fir_hash, 1);
        print(table);
        System.out.print("\n\nРехеширование: \n");
        table = rehash(table);
        print(table);


    }


    public static int hash(char[][] input) {
        int res = 0;
        int[] calc = new int[3];
        for (int j = 0; j < input.length; j++) {
            for (int i = 0; i < input[j].length; i++) {
                calc[j] = Arrays.hashCode(input[j]);
            }
        }
        res = calc[0] + calc[1] + calc[2];
        return res;
    }

    public static char[] transform(char[][] inp) {
        int length = 0;
        for (int j = 0; j < inp.length; j++) {
            length += inp[j].length;
        }
        length += 3; //для пробелов
        char[] result = new char[length];
        int index = 0;

        for (int j = 0; j < inp.length; j++) {
            for (int i = 0; i < inp[j].length; i++) {
                result[index++] = inp[j][i];
            }
            result[index++] = ' ';
        }
        return result;
    }

    public static char[][] transform_to_list(char[] inp) {
        int length = inp.length;
        char[][] result = new char[3][8];
        int index = 0;
        int j = 0;

        for (int i = 0; i < length; i++) {
            if (inp[i] != ' ') {
                result[index][j] = inp[i];
                j++;
            } else {
                j = 0;
                index++;
            }
        }
        return result;
    }

    public static Node[][] create_table() {
        Node[][] table = new Node[10][5];
        table[0][0] = new Node();
        table[0][0].key = 0;
        table[0][0].cur_pos = 0;
        for (int i = 1; i < 10; i++) {
            table[i][0] = new Node();
            table[i][0].key = i;
            table[i][0].cur_pos = 0;
            table[i - 1][0].next_node = table[i][0];
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j < 5; j++) {
                table[i][j] = new Node();
                table[i][j].key = i;
                table[i][j].cur_pos = j;
                table[i][j].prev_elem = table[i][j - 1];
                table[i][j - 1].next_elem = table[i][j];
            }
        }
        return table;
    }

    public static Node[][] fill_table(Node[][] table, char[][][] list) {

        for (int i = 0; i < list.length; i++) {
            int key_of_line = Math.abs(hash(list[i])) % 10;
            for (int j = 0; j < 5; j++) {
                if (table[key_of_line][j].data.length == 1) {
                    table[key_of_line][j].data = transform(list[i]);
                    break;
                }
            }
        }
        return table;
    }

    public static Node[][] rehash(Node[][] old_table) {
        int capacity = 0;
        Node current = old_table[0][0];
        while (current.next_node != null) {
            capacity = old_table[current.key][0].key;
            current = current.next_node;
        }
        int new_capacity = 2 * (capacity + 1);
        Node[][] new_table = new Node[new_capacity][5];
        new_table[0][0] = new Node();
        new_table[0][0].key = 0;
        new_table[0][0].cur_pos = 0;
        for (int i = 1; i < new_capacity; i++) {
            new_table[i][0] = new Node();
            new_table[i][0].key = i;
            new_table[i][0].cur_pos = 0;
            new_table[i - 1][0].next_node = new_table[i][0];
        }
        for (int i = 0; i < new_capacity; i++) {
            for (int j = 1; j < 5; j++) {
                new_table[i][j] = new Node();
                new_table[i][j].key = i;
                new_table[i][j].cur_pos = j;
                new_table[i][j].prev_elem = new_table[i][j - 1];
                new_table[i][j - 1].next_elem = new_table[i][j];
            }
        }
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < 5; j++) {
                if (old_table[i][j].data.length == 1) {
                    break;
                } else {
                    int key_of_line = Math.abs(hash(transform_to_list(old_table[i][j].data))) % new_capacity;
                    for (int z = 0; z < 5; z++) {
                        if (new_table[key_of_line][z].data.length == 1) {
                            new_table[key_of_line][z].data = old_table[i][j].data;
                            break;
                        }
                    }
                }
            }
        }
        return new_table;
    }

    public static void print(Node[][] table) {
        Node current = table[0][0];
        int lenght = 0;
        while (current.next_node != null) {
            lenght++;
            current = current.next_node;
        }

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < lenght + 1; i++) {
                if (table[i][j].data[0] == ' ') {
                    System.out.print("|");
                } else {
                    System.out.print(table[i][j].data);
                }
            }
            System.out.print("\n");
        }
    }
}