package matrice;

import java.text.DecimalFormat;

public class DenseMatrix {

    private int nRow;
    private int nCol;
    private double vals[][];

    ///////////////////////
    //                   //
    //   CONSTRUCTEURS   //
    //                   //
    ///////////////////////

    public DenseMatrix() {
        nRow = 0;
        nCol = 0;
        vals = null;
    }

    public DenseMatrix(double[][] mat) {
        nRow = mat.length;
        nCol = mat[0].length;
        vals = mat;
    }

    public DenseMatrix(int row, int col) {
        nRow = row;
        nCol = col;
        vals = new double[nRow][nCol];
    }

    //////////////////////
    //                  //
    //     METHODES     //
    //                  //
    //////////////////////

    public boolean memeDimension(DenseMatrix B) {
        return B.getColDimension() == this.nCol && B.getRowDimension() == this.nRow;
    }// memeDimension()

    public boolean matriceCarre() {
        return this.nRow == this.nCol;
    }// matriceCarre()

    public void write() {
        DecimalFormat formatEnDecimales = new DecimalFormat("0.00");
        for (int lig = 0; lig < nRow; lig++)
            for (int col = 0; col < nCol; col++)
                System.out.print((col == 0 ? "\n[" : "") + formatEnDecimales.format(get(lig, col)) + (col == nCol - 1 ? "]" : ", "));
    }// write()

    public DenseMatrix sum(DenseMatrix B) throws ExceptionMatrix {
        if (!this.memeDimension(B))
            throw new ExceptionMatrix("Invalid dimension");
        DenseMatrix matrice = this.copy();
        for (int lig = 0; lig < nRow; lig++)
            for (int col = 0; col < nCol; col++)
                matrice.vals[lig][col] += B.get(lig, col);
        return matrice;
    }// sum()

    public DenseMatrix minus(DenseMatrix B) throws ExceptionMatrix {
        if (!this.memeDimension(B))
            throw new ExceptionMatrix("Invalid dimension");
        DenseMatrix matrice = this.copy();
        for (int lig = 0; lig < nRow; lig++)
            for (int col = 0; col < nCol; col++)
                matrice.vals[lig][col] -= B.get(lig, col);
        return matrice;
    }// minus()

    public DenseMatrix mult(double s) {
        DenseMatrix matrice = this.copy();
        for (int lig = 0; lig < nRow; lig++)
            for (int col = 0; col < nCol; col++)
                matrice.vals[lig][col] *= s;
        return matrice;
    }// mult()

    public DenseMatrix mult(DenseMatrix B) throws ExceptionMatrix {
        if (nCol != B.getRowDimension())
            throw new ExceptionMatrix("Invalid dimensions");
        DenseMatrix matrice = new DenseMatrix(this.nRow, B.nCol);
        double aij = 0;
        for (int lig = 0; lig < nRow; lig++)
            for (int col2 = 0; col2 < B.getColDimension(); col2++) {
                for (int col = 0; col < nCol; col++)
                    aij += this.get(lig, col) * B.get(col, col2);
                matrice.vals[lig][col2] = aij;
                aij = 0;
            }
        return matrice;
    }// mult()

    public DenseMatrix transpose() {
        // retourne la transpos�e d'une matrice
        DenseMatrix matrice = new DenseMatrix(this.nCol, this.nRow);
        for (int lig = 0; lig < nRow; lig++)
            for (int col = 0; col < nCol; col++)
                matrice.vals[col][lig] = this.vals[lig][col];
        return matrice;
    }// transpose()

    public DenseMatrix copy() {
        // copie la matrice courante dans une nouvelle matrice de m�me type
        DenseMatrix copie = new DenseMatrix(this.nRow, this.nCol);
        for (int lig = 0; lig < nRow; lig++)
            for (int col = 0; col < nCol; col++)
                copie.vals[lig][col] = this.vals[lig][col];
        return copie;
    }// copy()

    public void zeros() {
        // met � z�ros tous les coefficients de la matrice
        for (int lig = 0; lig < nRow; lig++)
            for (int col = 0; col < nCol; col++)
                this.vals[lig][col] = 0;
    }// zeros()

    public void identity() throws ExceptionMatrix {
        if (!this.matriceCarre())
            throw new ExceptionMatrix("Identity must be a square matrix");
        // affecte l'identit� � la matrice courante
        this.zeros();
        for (int ligCol = 0; ligCol < nRow; ligCol++)
            this.vals[ligCol][ligCol] = 1;
    }// identity()

    public void random() {
        // affecte des valeurs g�n�r�es al�atoirement
        for (int lig = 0; lig < nRow; lig++)
            for (int col = 0; col < nCol; col++)
                this.vals[lig][col] = Math.random();
    }// random()

    //////////////////////
    //                  //
    //    ACCESSEURS    //
    //        ET        //
    //    MODIFIEURS    //
    //                  //
    //////////////////////

    public int getRowDimension() {
        return nRow;
    }

    public int getColDimension() {
        return nCol;
    }

    public double get(int i, int j) {
        return vals[i][j];
    }

    public void set(int i, int j, double aij) {
        vals[i][j] = aij;
    }

    public DenseMatrix get(int iStart, int iEnd, int jStart, int jEnd) throws ExceptionMatrix {
        // i : row ; j : col
        if (iStart > iEnd || jStart > jEnd || jStart < 0 || iStart < 0 || iEnd >= nRow || jEnd >= nCol)
            throw new ExceptionMatrix("Wrong coordinates");
        DenseMatrix matrice = new DenseMatrix(iEnd - iStart + 1, jEnd - jStart + 1);
        
        for (int i=0; iStart <= iEnd; iStart++, i++)
            for (int jStart2 = jStart, j=0; jStart2 <= jEnd; jStart2++, j++)
                matrice.vals[i][j] = this.vals[jStart2][iStart];
        
        /*
        for(int i=0;i<matrice.nRow;i++)
            for(int j=0;j<matrice.nCol;j++)
                matrice.set(i, j, this.get(j+jStart, i+iStart));
                */
        return matrice;
    }// get()

    public void set(DenseMatrix B, int iStart, int iEnd, int jStart, int jEnd) throws ExceptionMatrix {
        // i : row ; j : col
        if (iStart > iEnd || jStart > jEnd || jStart < 0 || iStart < 0 || iEnd > nRow || jEnd > nCol)
            throw new ExceptionMatrix("Wrong coordinates");
        for (; iStart <= iEnd; iStart++)
            for (int jStart2 = jStart; jStart2 <= jEnd; jStart2++)
                this.vals[iStart][jStart2] = B.vals[iStart][jStart2];
    }// set()

}// DenseMatrix
