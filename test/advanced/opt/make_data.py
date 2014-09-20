#!/usr/bin/python

from os import sys
from numpy import *
from random import random
from random import seed 

def main(argv = None):
    if argv == None:
        argv = sys.argv
        
    total_rand  = 100000
    overlap  = 100
    num_Apart = 10000
    num_Bpart = 9000

    n = total_rand
    m = overlap
    mmA = num_Apart
    mmB = num_Bpart
    top  = n
    idx  = arange( n )
    val  = array([ int(random()*n*10) for i in xrange(n)] )
    
    idxT = arange( m )
    idxA = arange( mmA )
    idxB = arange( mmB )
    
    seed( 0 )
    
    for i in xrange( m ):
        ii = int(random()*top) 
        idx[ii], idx[top-1] = idx[top-1], idx[ii]
        top = top - 1
        idxT[ i ] = idx[ top ]

    for i in xrange( mmA ):
        ii = int(random()*top) 
        idx[ii], idx[top-1] = idx[top-1], idx[ii]
        top = top - 1
        idxA[ i ] = idx[ top ]
        
    for i in xrange( mmB ):
        ii = int(random()*top)
        idx[ii], idx[top-1] = idx[top-1], idx[ii]
        top = top - 1
        idxB[ i ] = idx[ top ]
        
    fi = open( 'create-header.txt', 'r' )
    fo = open( 'opt-create.fwt', 'w' )
    
    fo.write( fi.read() )
    fi.close()

    for i in xrange( m ):
        fo.write('insert into TTT values('+str(idxT[i])+','+str(val[idxT[i]])+')\n;\n')
    
    for i in xrange( m ):
        fo.write('insert into TTA values('+str(idxT[i])+','+str(val[idxT[i]])+')\n;\n')
    
    for i in xrange( mmA ):
        fo.write('insert into TTA values('+str(idxA[i])+','+str(val[idxA[i]])+')\n;\n')
    
    for i in xrange( m ):
        fo.write('insert into TTB values('+str(idxT[i])+','+str(val[idxT[i]])+')\n;\n')
                                                                    
    for i in xrange( mmB ):
        fo.write('insert into TTB values('+str(idxB[i])+','+str(val[idxB[i]])+')\n;\n')

    for i in xrange( m ):
        fo.write('insert into CTA values(\''+str(idxT[i])+'\','+str(val[idxT[i]])+')\n;\n')
    
    for i in xrange( mmA ):
        fo.write('insert into CTA values(\''+str(idxA[i])+'\','+str(val[idxA[i]])+')\n;\n')
    
    for i in xrange( m ):
        fo.write('insert into CTB values(\''+str(idxT[i])+'\','+str(val[idxT[i]])+')\n;\n')
                                                                    
    for i in xrange( mmB ):
        fo.write('insert into CTB values(\''+str(idxB[i])+'\','+str(val[idxB[i]])+')\n;\n')
    
    fo.close()                                   
    return 0

if __name__ == '__main__':
    sys.exit( main() )


