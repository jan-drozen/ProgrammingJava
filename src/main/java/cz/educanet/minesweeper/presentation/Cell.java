package cz.educanet.minesweeper.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Cell extends JButton {

    private OnClick mouseClick;
    private OnClick contextClick;

    public Cell() {
        setBackground(Color.darkGray);
        setForeground(Color.white);
        setFont(new Font("", Font.PLAIN, 12));
        setMargin(new Insets(0, 0, 0, 0));

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1 && mouseClick != null)
                    mouseClick.onClick();
                else if(e.getButton() == MouseEvent.BUTTON3 && contextClick != null)
                    contextClick.onClick();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public OnClick getMouseClick() {
        return mouseClick;
    }

    public void setMouseClick(OnClick mouseClick) {
        this.mouseClick = mouseClick;
    }

    public OnClick getContextClick() {
        return contextClick;
    }

    public void setContextClick(OnClick contextClick) {
        this.contextClick = contextClick;
    }
}
