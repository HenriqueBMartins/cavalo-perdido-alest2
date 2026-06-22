from collections import deque

with open("testes/caso0.txt") as f:
    grid = [list(line.strip('\n')) for line in f if line.strip()]

rows = len(grid)
cols = len(grid[0])
print(f"Grid is {rows}x{cols}")

start = end = None
for r in range(rows):
    for c in range(cols):
        if grid[r][c] == 'C': start = (r, c)
        elif grid[r][c] == 'S': end = (r, c)

moves = [(-2,-1), (-2,1), (-1,-2), (-1,2), (1,-2), (1,2), (2,-1), (2,1)]
q = deque([(start, 0, [])])
visited = set([start])

while q:
    (r, c), dist, path = q.popleft()
    if (r, c) == end:
        print("Moves:", dist)
        for p in path + [(r, c)]: print(p)
        exit(0)
    for dr, dc in moves:
        nr, nc = (r + dr) % rows, (c + dc) % cols
        if (nr, nc) not in visited and grid[nr][nc] != 'x':
            visited.add((nr, nc))
            q.append(((nr, nc), dist + 1, path + [(r, c)]))
print("IMPOSSIBLE")
