import random
import queue


class SlidePuzzle:
    board = [[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]  # initial board
    sequence = []
    numbersForTheBoard = []
    goalBoard = ((1, 2, 3, 4), (5, 6, 7, 8), (9, 10, 11, 12), (13, 14, 15, 16))
    emptyBlockCoordinates = [0, 0]

    """---------- generate_elements method --------------------------------------------------------------------------"""
    def generate_elements(self):
        self.sequence = list(range(1, 17))  # a list from 1 to 16
        #random.shuffle(self.sequence)
        return self.sequence  # return the numbers from 1 to 16

    """---------- createBoard method---------------------------------------------------------------------------------"""
    def create_board(self):
        self.numbersForTheBoard = self.generate_elements()  # get a list of numbers from 1 - 16
        for row in range(4):  # go through each row
            for column in range(4):  # visit each column in the row
                self.board[row][column] = self.numbersForTheBoard.pop(0)  # each block get a number
                # This is just for printing preferences
                if len(str(self.board[row][column])) == 1:
                    print(str(self.board[row][column]) + "  ", end=" ")
                else:
                    print(str(self.board[row][column]) + " ", end=" ")
            print()
        print()
        """for row in range(4):
            print(self.board[row])  # print each row"""
        self.emptyBlockCoordinates = [3, 3]
        print("This is the initial empty block location: ", self.emptyBlockCoordinates)  # row, column

    """---------- moveUp method -------------------------------------------------------------------------------------"""
    def move_up(self):
        try:
            if self.emptyBlockCoordinates[0] != 0:
                temp = self.board[self.emptyBlockCoordinates[0] - 1][
                    self.emptyBlockCoordinates[1]]  # number above the empty
                self.board[self.emptyBlockCoordinates[0] - 1][self.emptyBlockCoordinates[1]] = 16  # replace # above
                self.board[self.emptyBlockCoordinates[0]][
                    self.emptyBlockCoordinates[1]] = temp  # put number where empty was
                self.emptyBlockCoordinates = [self.emptyBlockCoordinates[0] - 1, self.emptyBlockCoordinates[1]]
                # updated the coordinates where the empty block is

        except IndexError:
            print("Can't go up. Empty block is in the first row")

    """---------- MoveRight method ----------------------------------------------------------------------------------"""
    def move_right(self):
        try:
            if self.emptyBlockCoordinates[1] != 3:
                temp = self.board[self.emptyBlockCoordinates[0]][self.emptyBlockCoordinates[1]+1]
                self.board[self.emptyBlockCoordinates[0]][self.emptyBlockCoordinates[1] + 1] = 16
                self.board[self.emptyBlockCoordinates[0]][self.emptyBlockCoordinates[1]] = temp
                self.emptyBlockCoordinates = [self.emptyBlockCoordinates[0], self.emptyBlockCoordinates[1] + 1]
        except IndexError:
            print("can't go to the right. Empty block is in the rightmost column")

    """---------- move_down method ----------------------------------------------------------------------------------"""
    def move_down(self):
        try:
            if self.emptyBlockCoordinates[0] != 3:
                temp = self.board[self.emptyBlockCoordinates[0]+1][self.emptyBlockCoordinates[1]]
                self.board[self.emptyBlockCoordinates[0]+1][self.emptyBlockCoordinates[1]] = 16
                self.board[self.emptyBlockCoordinates[0]][self.emptyBlockCoordinates[1]] = temp
                self.emptyBlockCoordinates = [self.emptyBlockCoordinates[0]+1, self.emptyBlockCoordinates[1]]
        except IndexError:
            print("can't go down. Empty block is in the last row")

    """---------- move_left method ----------------------------------------------------------------------------------"""
    def move_left(self):
        try:
            if self.emptyBlockCoordinates[1] != 0:
                temp = self.board[self.emptyBlockCoordinates[0]][self.emptyBlockCoordinates[1]-1]
                self.board[self.emptyBlockCoordinates[0]][self.emptyBlockCoordinates[1]-1] = 16
                self.board[self.emptyBlockCoordinates[0]][self.emptyBlockCoordinates[1]] = temp
                self.emptyBlockCoordinates = [self.emptyBlockCoordinates[0], self.emptyBlockCoordinates[1]-1]
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
    """---------- print_board ---------------------------------------------------------------------------------------"""
    def print_board(self):
        for row in range(4):  # go through each row
            for column in range(4):  # visit each column in the row
                # This is just for printing preferences
                if len(str(self.board[row][column])) == 1:
                    print(str(self.board[row][column]) + "  ", end=" ")
                else:
                    print(str(self.board[row][column]) + " ", end=" ")
            print()
        print("\nThis is the empty block's location: ", self.emptyBlockCoordinates)

    """---------- bfs method ----------------------------------------------------------------------------------------"""
    def bfs(self):
        pass

    def create_queue(self):
        my_queue = queue.Queue()
        my_queue.put(1)
        return  my_queue.get()


def main():
    board = SlidePuzzle()
    board.create_board()
    board.shuffle_board()
    board.print_board()
    print(board.create_queue())


main()
