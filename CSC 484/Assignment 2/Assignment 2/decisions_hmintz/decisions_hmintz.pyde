import json
from array import *
import Queue as queue
import heapq
import collections
import random
locations = []
obsticals = []
knightLoc = PVector()
target = PVector()
grid = []
path = {}
searching = False
trail = []
gridScale = 6
currentAction = None
kingGold = None
worldLocation = "../worlds/test3.json"
done = False
fails = 0


class PriorityQueue:
    def __init__(self):
        self.elements = []
    
    def empty(self):
        return len(self.elements) == 0
    
    def put(self, item, priority):
        heapq.heappush(self.elements, (priority, item))
    
    def get(self):
        return heapq.heappop(self.elements)[1]
    
    
def setup():
    size(640, 480)
    frameRate(60)
    global knightLoc, obsticals, locations, grid, target, searching, path, hasState, wantState, actions, knightInv, currentAction, kingGold
    found = False
    knightLoc, locations, obsticals, hasState, wantState, kingGold = readShapes();
    graph = createGraph()
    grid = Grid(width/ gridScale, height / gridScale)
    grid.fillObs(graph)
    knightInv = [0]
    searching = False
    currentAction = None
    
    
    dc = decisionMaker(hasState, wantState)
    actions = dc.generate_plan()
   

    # kn = grid.grab(knightLoc.x, knightLoc.y)
    # n = kn.getNeighbors(grid)
    # for i in n:
    #     i.colSwap()

def move_to(pl):
    if(pl == "inkeeper"):
        loc = "tavern"
    elif (pl == "blacksmith"):
        loc = "forge"
        
    elif (pl == "lady lupa"):
        loc = "cave"
    else:
        loc = pl    
    target = None
    for i in locations:
        if (i[0] == loc):
            target = PVector(i[1], i[2])
    global searching, trail
    searching = True
    path, cost = find(grid, knightLoc, target)
    if path == None:
        searching = False;
        print("I cant go there")
    else : trail = build_path(grid, path, knightLoc, target)

def draw():
    global knightLoc, currentAction
    background(255)
    
    drawLocs()
    drawObs()
    drawKnight()
    # grid.drawGrid()
   
    
    if (searching and len(trail) > 0):
        next = trail[0]
        trail.remove(next)
        if (len(trail) == 0):                
            found = False
        knightLoc = next.topLeft
    else : 
        
             
         if (currentAction == "Greet King"):
            print("The knight greets the king and is given: %s Gold " % (kingGold) )
            knightInv[0] = knightInv[0] + kingGold
            currentAction = 'next'
            
            if (kingGold == 0):
                print("The Knight cannot make any money! He must fight Ramses with what he has")
                notPossible()
                currentAction = "lose"
                move_to("tar_pit")
                
         elif currentAction == "lose":
            print("The night has been defeated by Ramses")
            currentAction = "done"
            
         elif currentAction == "win":
            print("The night has defeated Rameses")
            currentAction = "done"
            
         elif currentAction == "done":
            doNothing = True
            
         if (actions == None):
              print("The knight sees no way to victory, he must fight Ramses now")
              notPossible()
              currentAction = "lose"
              move_to("tar_pit")
                
         if (len(actions) != 0):
            winCon = actions[len(actions) - 1]
            ac = actions[0]
            currentAction = ac
            actions.remove(ac)
            if (ac == "have gold"):
                if(knightInv[0] == 0):
                    move_to('castle')
                    currentAction = "Greet King"

            elif "travel" in ac:
                loc = ac[10:]
                try:
                    move_to(loc)
                except:
                    print("The knight cannot go where he needs to, must make a new plan")
                    incFails()
                    if fails != 4:
                        newPlan(winCon)
                    else: 
                        print("The knight sees no way to victory, he must fight Ramses now")
                        notPossible()
                        currentAction = "lose"
                        move_to("tar_pit")
               
            elif("get" in ac or "buy" in ac):
                if "axe" in ac:
                    print("The knight purchases a rusty axe from the blacksmith")
                    knightInv.append("axe")
                    knightInv[0] = knightInv[0] - 1                    
                elif "ale" in ac:
                   print("The knight purchases a pint of ale from the Innkeeper")
                   knightInv.append("ale")
                   knightInv[0] = knightInv[0] - 1  
                   
                elif "water" in ac:
                   print("The knight purchases a glass of water from the Innkeeper")
                   knightInv.append("water")
                   knightInv[0] = knightInv[0] - 1 
                elif "blade" in ac:
                   print("The Knight purchases a rusty blade from the blacksmith")
                   knightInv.append("blade")
                   knightInv[0] = knightInv[0] - 1 
            elif("use" in ac or "Give" in ac):
                if("axe" in ac and "axe" in knightInv):
                   print("The Knight obtains wood from the Tree using an axe")
                   knightInv.append("wood")
                if "water" in ac:
                    print("The knight obtains Wolfsbane by giving the tree water")
                    knightInv.remove("water")
                if "wolfsbane" in ac:
                    print("The knight trades wolfsbane for Frenrir with lady lupa")    
                   
            else:
                if len(actions) == 1:
                    winCon == actions[0]
                    if (winCon == 'Fire'):
                        print("The knight is prepared to fight Ramses with Fire")
                    if (winCon == 'Fenrir'):
                        print("The knight is prepared to fight Ramses with Fenrir")
                    if (winCon == 'Poisened Sword'):
                        print("the knight crafts a sword from a blade and wood")
                        print("The knight crafts a poisoned sword using wolfsbane and a sword")
                        print("The knight is prepared to fight Ramses a poisoned sword")
                    if (winCon == 'Poisened Fenrir'):
                        print("The knight crafts a poisoned fenrir using wolfsbane and fenrir")
                        print("The knight is prepared to fight Ramses a poisoned fenrir")
                        
                    currentAction = "win"
                    move_to("tar_pit")
                    
               
def notPossible():
    global actions
    actions = [];        

def newPlan(winCon):
    global actions
    dc = decisionMaker(hasState, wantState)
    
    while(actions[len(actions) - 1] == winCon):
        actions = dc.generate_plan()
def incFails():
    global fails
    fails += 1
    
            
        

    
def build_path(grid, path, knightLoc, target):
    ret = []
    targ = grid.grab(target.x, target.y)
    start = grid.grab(knightLoc.x, knightLoc.y)
    cur = targ
    next = None
    ret.insert(0, cur)
    while(cur != start):
        next = path[cur]
        ret.insert(0, cur)
        cur = next
    ret.insert(0, cur)
    return ret
    
def createGraph():
    ret = createGraphics(width, height)
    ret.beginDraw()
    for ob in obsticals:
        name = ob[0]
        # col = getColor(name)
        points = ob[1]
        ret.fill(0)
        ret.beginShape()
        for p in points:
            ret.vertex(p.x, p.y)
        ret.endShape(CLOSE)
    ret.endDraw();
    ret.loadPixels()
    return ret;
    
def drawKnight():
    imageMode(CENTER)
    img = loadImage("../icons/" + "knight.png")
    img.resize(grid.xlen * 2, grid.ylen * 2)
    image(img, knightLoc[0], knightLoc[1])
    
def moveKnight(next):
    knightLoc = next
    
def drawLocs():
    for loc in locations:
        name = "../icons/" + loc[0] + ".png"
        img = loadImage(name)
        img.resize(35, 35)
        image(img, loc[1], loc[2])
        
def drawObs():
    ob = obsticals[0]
    for ob in obsticals:
        name = ob[0]
        col = getColor(name)
        points = ob[1]
        fill(col)
        noStroke()
        beginShape()
        for p in points:
            vertex(p.x, p.y)
        endShape(CLOSE)
        
def getColor(name):
    if ("mountain" in name):
        return color(120)
    if("lake" in name):
        return color(0, 0, 255)
    if("forest" in name):
        return color(0, 255, 0)
    return color(120, 120, 0)
        
        
def readShapes():
    with open(worldLocation, "r") as read_file:
        data = json.load(read_file)
    
    start = data.get("knight_start")
    knight = PVector(start[0], start[1])
    
    keyLocs = data.get("key_locations")
    key_locations = []
    for val in keyLocs:
        place = keyLocs.get(val)
        ar = [str(val) , place[0], place[1]]
        key_locations.append(ar)
        
    obsticals = []
    obs = data.get("obstacles")
    
    for val in obs:
        ob = obs.get(val)
        points = []
        for point in ob:
            points.append(PVector(point[0], point[1]))
        obsticals.append([str(val), points])
        
        
    hasState = []
    wantState = []
    
    world = data.get("state_of_world")
    for el in world["Has"]:
        ar = [str(el[0]), str(el[1])]
        hasState.append(ar)
        
    for el in world["Wants"]:
        ar = [str(el[0]), str(el[1])]
        wantState.append(ar)
        
        
    kinggold = int(data.get("greet_king"))
    return knight, key_locations, obsticals, hasState, wantState, kinggold






def find(grid, start, target):
    startBlock = grid.grab(start.x, start.y)
    targBlock = grid.grab(target.x, target.y)
    if (targBlock.col == 0):
        return None, None
    front = PriorityQueue()
    front.put(startBlock, 0)
    path = {}
    cost = {}
    path[startBlock] = None
    cost[startBlock] = 0
    found = False
    
    while not front.empty():
        cur = front.get()
        if (cur != None and cur.col != 0):
            if(cur.x == targBlock.x and cur.y == targBlock.y):
                found = True
                break
            for next in cur.getNeighbors(grid):
                new_cost = cost[cur] + 1
                if next not in cost or new_cost < cost[next]:
                    cost[next] = new_cost
                    h = + heuristic(startBlock, targBlock)
                    dx1 = cur.x - targBlock.x
                    dy1 = cur.y - targBlock.y
                    dx2 = startBlock.x - targBlock.x
                    dy2 = startBlock.y - targBlock.y
                    cr = abs(dx1*dy2 - dx2*dy1)
                    h = h + cr*.001
                    prio = new_cost + h
                    front.put(next, prio)
                    path[next] = cur;   
    if targBlock not in path:
        return None, None
        
    return path, cost
    


def heuristic(cur, targ):
    (x1, y1) = (cur.x, cur.y)
    (x2, y2) = (targ.x, targ.y)
    return abs(x1 - x2) + abs(y1- y2)



class Grid():
    def __init__(self, xCount=640, yCount=480):
        self.xblock = xCount;
        self.yblock = yCount;
        
        self.xlen = width / xCount
        self.ylen = height / yCount
        
        self.grid = [Block()] * xCount * yCount
        self.fillGrid();
        
    def fillGrid(self):
        for y in range(0, self.yblock):
            for x in range(0, self.xblock):
                ind = self.xblock * y + x
                self.grid[ind] = Block(self.xlen, self.ylen, x, y)
                
    def drawGrid(self):
       for y in range(0, self.yblock):
            for x in range(0, self.xblock):
                 ind = self.xblock * y + x
                 self.grid[ind].drawBlock()
    def grab(self, x, y):
        xind = int(x / self.xlen)
        yind = int (y / self.ylen)
        
        if (xind < 0 or xind >= self.xblock or yind < 0 or yind >= self.yblock):
            return None
        else:
            return self.grid[self.xblock * yind + xind]
        
    def ind(self, x, y):
        try:
            ind =  self.xblock * y + x
            return self.grid[ind]
            break
        except:
            return None
        
    def fillObs(self, graph):
        for y in range(0, self.yblock):
            for x in range(0, self.xblock):
                 ind = self.xblock * y + x
                 if self.grid[ind].containsObs(graph):
                      self.grid[ind].colSwap()
        
class Block():
    def __init__(self, xlen=0, ylen=0, x=0, y=0, col=-1):
        self.xlen = xlen
        self.ylen = ylen
        self.x = x
        self.y = y
        self.topLeft = PVector(x * xlen, y * ylen)
        self.col = col
    
    def drawBlock(self):
        stroke(0)
        if (self.col == -1):
            noFill()
        else:
            fill(self.col)
        rect(self.topLeft.x, self.topLeft.y, self.xlen, self.ylen)
        
    def colSwap(self):
        if (self.col == -1):
            self.col = 0;
        else:
            self.col = -1
            
    def containsObs(self, graph):
        xind = int( self.x * self.xlen)
        yind = int(self.y * self.ylen)
        
        xrang = range(xind, xind + self.xlen)
        yrange = range(yind, yind + self.ylen)
        
        for i in yrange:
            for j in xrang:
               if (graph.get(j, i) != 0):
                   return True
        
        return False
    
    def getNeighbors(self, grid):
        ret = []
        ret.append(grid.ind(self.x + 1 , self.y))
        ret.append(grid.ind(self.x + 1 , self.y + 1))
        ret.append(grid.ind(self.x + 1 , self.y - 1))
        ret.append(grid.ind(self.x - 1 , self.y))
        ret.append(grid.ind(self.x - 1 , self.y - 1))
        ret.append(grid.ind(self.x   , self.y + 1))
        ret.append(grid.ind(self.x - 1 , self.y + 1))
        ret.append(grid.ind(self.x  , self.y - 1))
        
        return ret;
        
        
        


class decisionMaker:
    
    has = None;
    want = None;
    greetKing = True;
    knightInv = []
    primaryWinConditions = ["Fire", "Fenrir", "Poisened Sword", "Poisened Fenrir"]
    def __init__(self, has, want):
        self.has = has
        self.want = want
        
    def can_fill(self, desire):
        if(desire == "Wood"):
            return self.can_fill("Axe")
        if(desire == "Sword"):
            return self.can_fill("Blade") and self.can_fill("Wood")
        else: chek = desire
        for el in self.has:
            if(el[0] == chek or el[1] == chek):
                return True
        return False
            
    def fill(self, desire):
        steps = [desire]
        step = None
        if desire == "Ale":
            step = self.fill("buy ale from inkeeper")
        if desire == "Wood":
            step = self.fill("use axe on Tree Sprit")
        if desire == "Wolfsbane":
            step = self.fill("use water on Tree Spirit")
        if desire == "Sword":
           step = self.fill("cobmine blade and wood")
        if desire == "Fenrir":
            step = self.fill("Give wolfsbane to Lady Lupa")
            
        if desire == ("buy ale from inkeeper"):
            step = ["have gold", "travel to tavern"]
            step = step[::-1]
        if desire == ("use axe on Tree Sprit"):
            step = [self.fill("get axe"), "travel to tree"]
            step = step[::-1]
        if desire == "use water on Tree Spirit":
            step = [self.fill("buy water from inkeeper"), "travel to tree"]
            step = step[::-1]
        if desire == "Give wolfsbane to Lady Lupa":
            step = [self.fill("use water on Tree Spirit"), "travel to lady lupa"]
            step = step[::-1]
        if desire == "cobmine blade and wood":
            step = [self.fill("get blade"), self.fill("get wood")]
            
        if desire == "get blade":
            step = ["have gold", "travel to blacksmith"]
            step = step[::-1]
        if desire == "get wood":
            step = self.fill("use axe on Tree Sprit")
        
        if desire == "buy water from inkeeper":
            step = ["have gold", "travel to inkeeper"]
            step = step[::-1]
        if desire == "get axe":
            step = ["have gold", "travel to blacksmith"]
            step = step[::-1]
        
        steps.append(step)
        return steps
    
    #will generate a plan to execute based on a randomly selected win condition
    def generate_plan(self):
        win = False
        desire = []
        targetWin = None
        steps = []
        while(not win and len(self.primaryWinConditions) > 0):
            win = True
            targ = random.randint(0, len(self.primaryWinConditions) - 1)
            targetWin = self.primaryWinConditions[targ]
            self.primaryWinConditions.remove(targetWin)
            
            if(targetWin == "Fire"):
                desire = ["Ale", "Wood"]
            if(targetWin == "Fenrir"):
                desire  =["Fenrir"]
            if(targetWin == "Poisened Sword"):
                desire = ["Sword", "Wolfsbane"]
            if(targetWin == "Poisened Fenrir"):
                desire = ["Fenrir", "Wolfsbane"]
                
                
            for des in desire:
                if not self.can_fill(des):
                    win = False
        
        if win == False:
            return None
        steps.append(targetWin)
        
        for des in desire:
            desSteps = self.fill(des)
            steps.append(desSteps)
    
        trail = []
                    
        for i in steps:
            if (type(i) == str):
                trail.insert(0, i)
            else:
                for j in i:
                    if (type(j) == str):
                        trail.insert(0, j)
                        
                    else:
                        for k in j:
                            if (type(k) == str):
                                trail.insert(0, k)
                            else:
                                for f in k:
                                    if (type(f) == str):
                                        trail.insert(0, f)
                                    else:
                                        for g in f:
                                            if (type(g) == str):
                                                trail.insert(0, g)
                                            else:
                                                for h in g:
                                                    if(type(h) == str):
                                                      trail.insert(0, h)
                                                    else :
                                                        for p in h:
                                                            if (type(p) == str):
                                                               trail.insert(0, p)
                                                            else:
                                                                for q in p:
                                                                    if(type(q) == str):
                                                                        trail.insert(0, q)
                                                                    else:
                                                                        for z in q:
                                                                            trail.insert(0, z)
                                                                    
                
        return trail
                
            
                
                
    
        
