from random import shuffle

class SlidePuzzle:
#---------- generateElements method ------------------------------------------------------------------------------------
    def generateElements(self):
        self.elements = list(range(1,17))# a list from 1 to 16
        return self.elements#return the numbers from 1 to 16

#---------- createBoard method------------------------------------------------------------------------------------------
    def createBoard(self):
        self.board = [[0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]]#inital board
        self.numbersForTheBoard = self.generateElements()#get a list of numbers from 1 - 16
        self.goalBoard  = ((1,2,3,4),(5,6,7,8),(9,10,11,12),(13,14,15,16))#We want our board to be like this one
        for row in range(4):#go through each row
            for column in range(4):#visit each column in the row
                self.board[row][column] = self.numbersForTheBoard.pop(0)#each block get a number
                #This is just for printing preferences
                if len(str(self.board[row][column])) == 1:
                    print(str(self.board[row][column]) + "  ", end= " ")
                else:
                    print(str(self.board[row][column]) + " ", end=" ")
            print()
        print()
        for row in range(4):
            print(self.board[row])#print each row
        self.emptyBlockCoordinates= [3,3]#row, column
        print("\nWe want this board: ",self.goalBoard)

#---------- moveUp method ----------------------------------------------------------------------------------------------
    def moveUp(self):
        try:
            if self.emptyBlockCoordinates[0] != 0:
                temp = self.board[self.emptyBlockCoordinates[0]-1][self.emptyBlockCoordinates[1]]#number above the empty
                self.board[self.emptyBlockCoordinates[0]-1][self.emptyBlockCoordinates[1]] = 16 #replace # above
                self.board[self.emptyBlockCoordinates[0]][self.emptyBlockCoordinates[1]] = temp #put number where empty was
                self.emptyBlockCoordinates = [self.emptyBlockCoordinates[0]-1,self.emptyBlockCoordinates[1]]
                #updated the coordinates where the empty block is

        except IndexError:
            print("Can't go up. Empty block is in the first row")


def main():
    board = SlidePuzzle()
    board.createBoard()

main()