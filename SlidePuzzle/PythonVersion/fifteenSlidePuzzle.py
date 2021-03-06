import random


class SlidePuzzle:
    direction = {"LEFT": "l", "RIGHT": "r", "UP": "u", "DOWN": "d"}
    board = [[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]
    path = []
    solvePuzzleUsingMethod = ""
    lastMove = ""
    numbers = []

    """---------- constructor -----------------------------------------------"""
    def __init__(self):
        i = 1
        for row in range(4):
            for column in range(4):
                self.board[row][column] = i
                i += 1

    """---------- print board method ----------------------------------------"""
    def print_board(self):
        string = ""
        for row in self.board:
            for number in row:
                if len(str(number)) == 1:
                    string += "  " + str(number)
                elif len(str(number)) > 1:
                    string += " " + str(number)
            string += "\n"
        print(string)

    """---------- get blank space postion method ----------------------------"""
    def get_blank_space_position(self):
        for i in range(4):
            for j in range(4):
                if self.board[i][j] == 16:
                    return [i, j]

    """---------- swap elements method --------------------------------------"""
    def swap_items(self, row1, column1, row2, column2):
        temp = self.board[row1][column1]
        self.board[row1][column1] = self.board[row2][column2]
        self.board[row2][column2] = temp

    """---------- get move method -------------------------------------------"""
    def get_move(self, piece_to_move):
        blank_position = self.get_blank_space_position()
        row = blank_position[0]
        column = blank_position[1]
        if row > 0 and piece_to_move == self.board[row-1][column]:
            return self.direction["DOWN"]
        elif row < 3 and piece_to_move == self.board[row+1][column]:
            return self.direction["UP"]
        elif column > 0 and piece_to_move == self.board[row][column-1]:
            return self.direction["RIGHT"]
        elif column < 3 and piece_to_move == self.board[row][column+1]:
            return self.direction["LEFT"]

    """---------- move blank space method -----------------------------------"""
    def move(self, piece_to_move):
        move = self.get_move(piece_to_move)

        if move is not None:
            blank_space_position = self.get_blank_space_position()
            row = blank_space_position[0]
            column = blank_space_position[1]
            if move == "u":
                self.swap_items(row, column, row+1, column)
            elif move == "r":
                self.swap_items(row, column, row, column-1)
            elif move == "d":
                self.swap_items(row, column, row-1, column)
            elif move == "l":
                self.swap_items(row, column, row, column+1)

            if move is not None:
                self.lastMove = piece_to_move
            return move

    """---------- is goal state method --------------------------------------"""
    def is_goal_state_reached(self):
        goal = [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12], [13, 14, 15, 16]]
        for row in range(4):
            for column in range(4):
                if self.board[row][column] != goal[row][column]:
                        return False
        return True

    """---------- get a copy of board method --------------------------------"""
    def get_a_copy_of_the_board(self):
        copy_of_puzzle = SlidePuzzle()
        for row in range(4):
            for column in range(4):
                copy_of_puzzle.board[row][column] = self.board[row][column]

        for i in range(len(self.path)):
            copy_of_puzzle.path.append(self.path[i])
        return copy_of_puzzle

    """---------- get all allowed movements ---------------------------------"""
    def get_allowed_movements(self):
        allowed_moves = []
        for row in range(4):
            for column in range(4):
                piece = self.board[row][column]
                if self.get_move(piece) is not None:
                    allowed_moves.append(piece)
        return allowed_moves

    """---------- visit method ----------------------------------------------"""
    def visit(self):
        children = []
        allowed_moves = self.get_allowed_movements()
        for i in range(len(allowed_moves)):
            move = allowed_moves[i]
            if move != self.lastMove:
                new_board = self.get_a_copy_of_the_board()
                new_board.move(move)
                new_board.path.append(move)
                children.append(new_board)
        return children

    """---------- moveUp method -------------------------------------------------------------------------------------"""

    def move_up(self):
        empty_block = self.get_blank_space_position()
        try:
            if empty_block[0] != 0:
                temp = self.board[empty_block[0] - 1][empty_block[1]]  # number above the empty
                self.board[empty_block[0] - 1][empty_block[1]] = 16  # replace # above
                self.board[empty_block[0]][empty_block[1]] = temp  # put number where empty was
                # updated the coordinates where the empty block is

        except IndexError:
            print("Can't go up. Empty block is in the first row")

    """---------- MoveRight method ----------------------------------------------------------------------------------"""

    def move_right(self):
        empty_block = self.get_blank_space_position()
        try:
            if empty_block[1] != 3:
                temp = self.board[empty_block[0]][empty_block[1] + 1]
                self.board[empty_block[0]][empty_block[1] + 1] = 16
                self.board[empty_block[0]][empty_block[1]] = temp
        except IndexError:
            print("can't go to the right. Empty block is in the rightmost column")

    """---------- move_down method ----------------------------------------------------------------------------------"""

    def move_down(self):
        empty_block = self.get_blank_space_position()
        try:
            if empty_block[0] != 3:
                temp = self.board[empty_block[0] + 1][empty_block[1]]
                self.board[empty_block[0] + 1][empty_block[1]] = 16
                self.board[empty_block[0]][empty_block[1]] = temp
        except IndexError:
            print("can't go down. Empty block is in the last row")

    """---------- move_left method ----------------------------------------------------------------------------------"""

    def move_left(self):
        empty_block = self.get_blank_space_position()
        try:
            if empty_block[1] != 0:
                temp = self.board[empty_block[0]][empty_block[1] - 1]
                self.board[empty_block[0]][empty_block[1] - 1] = 16
                self.board[empty_block[0]][empty_block[1]] = temp
        except IndexError:
            print("Can't go to the left. Empty block is in the leftmost column")

    """---------- shuffle method ------------------------------------------------------------------------------------"""

    def shuffle_board(self):
        for i in range(150):
            direction = random.randrange(1, 5)
            if direction == 1:
                self.move_up()
            elif direction == 2:
                self.move_right()
            elif direction == 3:
                self.move_down()
            elif direction == 4:
                self.move_left()

    """---------- Breath First Search method --------------------------------"""
    def bfs(self):
        my_file = open("result.txt", "w+")
        my_file.write("Board:\n")
        for i in range(4):
            for j in range(4):
                if i == 3 and j == 3:
                    my_file.write(str(self.board[i][j]))#no extra espace at the last number
                else:
                    my_file.write(str(self.board[i][j]) + " ")

        starting_state = self.get_a_copy_of_the_board()
        starting_state.path = []
        states = [starting_state]

        i = 0
        while i < 15:
            state = states.pop(0)
            if state.is_goal_state_reached():
                print("board is solved")
                state.print_board()
                my_file.close()
                return state.path
            states = states + state.visit()
            i += 1
        print("15 steps wasn't enough to find a solution for the puzzle")


"""---------- main ----------------------------------------------------------"""


def main():
    board = SlidePuzzle()
    board.shuffle_board()
    board.print_board()
    board.bfs()


main()
