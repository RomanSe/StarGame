package ru.rosemenov.stargame.math;


import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

/**
 * Утилита для работы с матрицами
 */
public class MatrixUtils {

    private MatrixUtils() {
    }

    /**
     * Расчёт матрицы перехода 4x4
     * @param mat итоговая матрица преобразований
     * @param src исходный квадрат
     * @param dst итоговый квадрат
     */
    public static void calcTransitionMatrix(Matrix4 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        mat.idt().translate(dst.getX(), dst.getY(), 0f).scale(scaleX, scaleY, 1f).translate(-src.getX(), -src.getY(), 0f);
    }

    /**
     * Расчёт матрицы перехода 3x3
     * @param mat итоговая матрица преобразований
     * @param src исходный квадрат
     * @param dst итоговый квадрат
     */
    public static void calcTransitionMatrix(Matrix3 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        mat.idt().translate(dst.getX(), dst.getY()).scale(scaleX, scaleY).translate(-src.getX(), -src.getY());
    }

    /**
     * Расчёт специальной матрицы 3x3 для интерпретации ввода
     * @param mat итоговая матрица преобразований
     * @param src исходный квадрат
     * @param dst итоговый квадрат
     */
    public static void calcInputMatrix(Matrix3 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        mat.idt().translate(dst.getX(), dst.getY()).scale(scaleX, -scaleY).translate(-src.getX(), -src.getY());
    }

}
