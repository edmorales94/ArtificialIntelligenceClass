import random
import queue


class SlidePuzzle:
    board = [[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]  # initial board
    sequence = []
    numbersForTheBoard = []
    goalBoard = ((1, 2, 3, 4), (5, 6, 7, 8), (9, 10, 11, 12), (13, 14, 15, 16))
    emptyBlockCoordinates = [0, 0]
    direction = {"LEFT": "left", "RIGHT": "right"}

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

    """---------- convert_to_tuple ----------------------------------------------------------------------------------"""
    def convert_to_tuple(self, board):
        result = []
        for i in range(4):
            result.append(board[i])
        return tuple(result)

    """---------- convert to list -----------------------------------------------------------------------------------"""
    def convert_to_list(self, tuple):
        result = []
        for i in tuple:
            result.append(list(i))
        return result

    """---------- match method --------------------------------------------------------------------------------------"""
    def match(self, copy_board):
        a = SlidePuzzle()
        a.create_board()
        a.board = copy_board
        for row in range(4):
            for column in range(4):
                if a.board[row][column] == 16:
                    a.emptyBlockCoordinates = [row, column]
        result = []
        for i in a.board:
            result.append(list(i))
        a.board = result
        return a

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
        startingPuzzle = self.convert_to_tuple(self.board)
        predictionForDirections = {}
        nodesVisited = []
        nodesToExplore = queue.Queue()
        nodesToExplore.put(startingPuzzle)

        while nodesToExplore.qsize() > 0:
            temp = nodesToExplore.get()  # get the first node in the queue(FIFO)

            if temp == self.goalBoard:
                path = []
                while temp != startingPuzzle:
                    path.append(predictionForDirections[temp][1])
                    temp = predictionForDirections[temp][0]
                return path[::-1]

            if temp not in nodesVisited:
                nodesVisited.append(temp)
                temporary_board = self.match(temp)
                temporary_board.move_up()
                if self.convert_to_tuple(temporary_board) != temp:
                    nodesToExplore.put(self.convert_to_tuple(temporary_board))
                    if self.convert_to_tuple(temporary_board) not in predictionForDirections.keys():
                        predictionForDirections[self.convert_to_tuple(temporary_board.board)] = [temporary_board, "up"]

                temporary_board = self.match(temp)
                temporary_board.move_down()
                if self.convert_to_tuple(temporary_board.board) != temp:
                    nodesToExplore.put(self.convert_to_tuple(temporary_board.board))
                    if self.convert_to_tuple(temporary_board) not in predictionForDirections.keys():
                        predictionForDirections[self.convert_to_tuple(temporary_board.board)] = [temp, "down"]

                temporary_board = self.match(temp)
                temporary_board.move_right()
                if self.convert_to_tuple(temporary_board.board) != temp:
                    nodesToExplore.put(self.convert_to_tuple(temporary_board.board))
                    if self.convert_to_tuple(temporary_board) not in predictionForDirections.keys():
                        predictionForDirections[self.convert_to_tuple(temporary_board.board)] = [temp, "right"]

                temporary_board = self.match(temp)
                temporary_board.move_left()
                if self.convert_to_tuple(temporary_board.board) != temp:
                    nodesToExplore.put(self.convert_to_tuple(temporary_board.board))
                    if self.convert_to_tuple(temporary_board) not in predictionForDirections.keys():
                        predictionForDirections[self.convert_to_tuple(temporary_board.board)] = [temp, "left"]
def main():
    board = SlidePuzzle()
    board.create_board()
    board.shuffle_board()
    board.print_board()
    print(board.bfs())


main()
