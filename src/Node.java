class Node {
    char[] data;
    Node next_elem;
    Node next_node;
    Node prev_elem;
    int cur_pos;
    int key;

    Node() {
        this.data = new char[]{' '};
        this.next_elem = null;
        this.prev_elem = null;
        this.next_node = null;
        this.cur_pos = 0;
        this.key = 0;
    }

    public void delite(int del_key, int del_pos) {
        Node current = this;
        while (current.next_node != null) {
            Node cur_start = current.next_node;
            if (current.key == del_key) {
                if (del_pos == 0) {
                    current.data = new char[]{' '};
                    return;
                }
                else{
                    while (current.next_elem != null) {
                        if (current.cur_pos == del_pos) {
                            current.data = new char[]{' '};
                            return;
                        } else {
                            current = current.next_elem;
                        }
                    }
                }
            } else {
                current = cur_start;
            }
        }
    }

    public void insert(char[][] value) {
        int key = Math.abs(run_code.hash(value)) % 10;
        Node current = this;
        while (current.next_node != null) {
            Node cur_start = current.next_node;
            if (current.key == key) {
                while (current.next_elem != null) {
                    if (current.data.length == 1) {
                        current.data = run_code.transform(value);
                        break;
                    } else {
                        current = current.next_elem;
                    }
                }
            } else {
                current = cur_start;
            }
        }
    }

    public char[] find(int key, int pos) {
        Node current = this;

        while (current.next_node != null) {
            Node cur_start = current.next_node;
            if (current.key == key) {
                while (current.next_elem != null) {
                    if (current.cur_pos == pos) {
                        return current.data;
                    } else {
                        current = current.next_elem;
                    }
                }
            } else {
                current = cur_start;
            }
        }
        return new char[]{'Н', 'е', ' ', 'н', 'а', 'ш', 'л', 'о', 'с', 'ь'};
    }


}