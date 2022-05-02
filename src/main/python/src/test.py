import math


def demo(a, b, c):
    d = b ** 2 - 4 * a * c
    if d > 0:
        disc = math.sqrt(d)
        root1 = (-b + disc) / (2 * a)
        root2 = (-b - disc) / (2 * a)
        return root1, root2
    elif d == 0:
        return -b / (2 * a)
    else:
        return "This equation has no roots"


class Solver:
    pass


if __name__ == '__main__':
    solver = Solver()
    a = int(input("a: "))
    b = int(input("b: "))
    c = int(input("c: "))
    result = demo(a, b, c)
    print(result)