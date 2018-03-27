while True:
    flag=True
    while flag:
        S = open("mj2p.txt","r")
        m=S.read()
        S.close()
        flag= m==""
    W = open("mp2j.txt","w")
    W.write(m+" back from python ")
    W.close()

    
