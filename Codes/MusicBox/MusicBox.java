import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MusicBox extends JFrame implements Runnable, ActionListener, AdjustmentListener
{
    JToggleButton[][] buttons;
    boolean[][] oldButtons;
    JToggleButton playButton;
    JButton clearButton, resetButton;
    JPanel buttonPanel, controlPanel;
    JScrollPane scrollPane;
    JMenu instruments, file, addRemove;
    JMenuItem bell, glockenspiel, marimba, oboe, oh_ah, piano, save, load, addColumn, add10Columns, removeColumn, remove10Columns;
    ArrayList<String> noteNames = new ArrayList<String>();
    String[] notesAdj = {"C", "CSharp", "D", "DSharp", "E", "F", "FSharp", "G", "GSharp", "A", "ASharp", "B"};
    ArrayList<String> buttonNames = new ArrayList<String>();
    ArrayList<Clip> clips = new ArrayList<Clip>();
    String[] instrumentNames = {"Bell", "Glockenspiel", "Marimba", "Oboe", "Oh_Ah", "Piano"};
    Thread timingThread;
    int c = 0;
    JMenuBar menuBar;
    boolean isPlaying = false;
    JScrollBar tempoBar;
    int tempo = 200;
    String currDirectory = System.getProperty("user.dir");
    JFileChooser fileChooser = new JFileChooser(currDirectory);
    Character[][] song;


    public MusicBox() {
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        }
        catch (Exception ignored)
        {
        }
        //add notes
        noteNames.add("C4");
        noteNames.add("B3");
        noteNames.add("A#3");
        noteNames.add("A3");
        noteNames.add("G#3");
        noteNames.add("G3");
        noteNames.add("F#3");
        noteNames.add("F3");
        noteNames.add("E3");
        noteNames.add("D#3");
        noteNames.add("D3");
        noteNames.add("C#3");
        noteNames.add("C3");
        noteNames.add("B2");
        noteNames.add("A#2");
        noteNames.add("A2");
        noteNames.add("G#2");
        noteNames.add("G2");
        noteNames.add("F#2");
        noteNames.add("F2");
        noteNames.add("E2");
        noteNames.add("D#2");
        noteNames.add("D2");
        noteNames.add("C#2");
        noteNames.add("C2");
        noteNames.add("B1");
        noteNames.add("A#1");
        noteNames.add("A1");
        noteNames.add("G#1");
        noteNames.add("G1");
        noteNames.add("F#1");
        noteNames.add("F1");
        noteNames.add("E1");
        noteNames.add("D#1");
        noteNames.add("D1");
        noteNames.add("C#1");
        noteNames.add("C1");
        //instantiate key buttons
        createButtons(37, 50);
        //instantiate panels
        controlPanel = new JPanel(); //control
        controlPanel.setLayout(new GridLayout(1, 2));
        setSize(3330, 4500); //frame
        //instantiate control buttons
        playButton = new JToggleButton("Play");
        playButton.addActionListener(this);
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        //instantiate instruments menu
        instruments = new JMenu("Instruments");
        instruments.setMnemonic(KeyEvent.VK_I);
        menuBar = new JMenuBar();
        bell = new JMenuItem("Bell");
        bell.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        bell.addActionListener(this);
        glockenspiel = new JMenuItem("Glockenspiel");
        glockenspiel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        glockenspiel.addActionListener(this);
        marimba = new JMenuItem("Marimba");
        marimba.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        marimba.addActionListener(this);
        oboe = new JMenuItem("Oboe");
        oboe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        oboe.addActionListener(this);
        oh_ah = new JMenuItem("Oh_Ah");
        oh_ah.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        oh_ah.addActionListener(this);
        piano = new JMenuItem("Piano");
        piano.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        piano.addActionListener(this);
        //add instruments menu items to menu
        instruments.add(bell);
        instruments.add(glockenspiel);
        instruments.add(marimba);
        instruments.add(oboe);
        instruments.add(oh_ah);
        instruments.add(piano);
        //instantiate file menu
        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);
        load = new JMenuItem("Load");
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        load.addActionListener(this);
        //add file menu items to menu
        file.add(save);
        file.add(load);
        //instantiate addremove menu
        addRemove = new JMenu("Add/Remove");
        addColumn = new JMenuItem("Add Column");
        addColumn.addActionListener(this);
        removeColumn = new JMenuItem("Remove Column");
        removeColumn.addActionListener(this);
        add10Columns = new JMenuItem("Add 10 Columns");
        add10Columns.addActionListener(this);
        remove10Columns = new JMenuItem("Remove 10 Columns");
        remove10Columns.addActionListener(this);
        //add addremove menu items to menu
        addRemove.add(addColumn);
        addRemove.add(removeColumn);
        addRemove.add(add10Columns);
        addRemove.add(remove10Columns);

        loadTones(instrumentNames[0]);
        //instantiate file menu
        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);
        load = new JMenuItem("Load");
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        load.addActionListener(this);
        //add file menu items to menu
        file.add(save);
        file.add(load);


        //add clips
        loadTones(instrumentNames[0]);
        //attach buttons to panel
        for (int x = 0; x < buttons.length; x++)
        {
            for (int y = 0; y < buttons[x].length; y++) 
            {
                buttonPanel.add(buttons[x][y]);
            }
        }
        //tempo
        tempoBar = new JScrollBar(JScrollBar.HORIZONTAL, 200, 0, 50, 350);
        tempoBar.addAdjustmentListener(this);
        //add to hierarchy
        controlPanel.add(playButton);
        controlPanel.add(clearButton);
        controlPanel.add(resetButton);
        menuBar.add(file);
        menuBar.add(instruments);
        menuBar.add(controlPanel);

        addRemove = new JMenu("Add/Remove");
        addColumn = new JMenuItem("Add Column");
        addColumn.addActionListener(this);
        removeColumn = new JMenuItem("Remove Column");
        removeColumn.addActionListener(this);
        add10Columns = new JMenuItem("Add 10 Columns");
        add10Columns.addActionListener(this);
        remove10Columns = new JMenuItem("Remove 10 Columns");
        remove10Columns.addActionListener(this);

        addRemove.add(addColumn);
        addRemove.add(removeColumn);
        addRemove.add(add10Columns);
        addRemove.add(remove10Columns);

        tempoBar = new JScrollBar(JScrollBar.HORIZONTAL, 200, 0, 50, 350);
        tempoBar.addAdjustmentListener(this);
        //add to hierarchy
        controlPanel.add(playButton);
        controlPanel.add(clearButton);
        controlPanel.add(resetButton);
        menuBar.add(file);
        menuBar.add(instruments);
        menuBar.add(controlPanel);
        menuBar.add(addRemove);
        this.add(tempoBar, BorderLayout.SOUTH);
        this.setJMenuBar(menuBar);
        this.add(scrollPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //thread
        timingThread = new Thread(this);
        timingThread.start();
    }
    public static void main(String[] args) {
        MusicBox musicBox = new MusicBox();
    }
    public void loadTones (String initInstrument)
    {
        //load tones
        try
        {
            for(int x = 0; x < noteNames.size(); x++)
            {
                String note=noteNames.get(x).replaceAll("#","Sharp");

                String str = "C:/Users/proow/OneDrive/Documents/Piano" + initInstrument + "/" + initInstrument + " - " + note + ".wav";
                System.out.println(str);
                //URL url = this.getClass().getClassLoader().getResource(str);
                File file = new File(str);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
                clips.add(AudioSystem.getClip());
                clips.get(x).open(audioIn);
            }
        }
        catch (UnsupportedAudioFileException|IOException|LineUnavailableException ignored)
        {

        }
    }
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        // TODO Auto-generated method stub
        tempo = tempoBar.getValue();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == playButton)
        {
            if (isPlaying == false)
            {
                isPlaying = true;
                playButton.setText("Stop");
            }
            else
            {
                isPlaying = false;
                playButton.setText("PLay");

            }
            c = 0;
        }
        else if (e.getSource() == clearButton)
        {
            clear();
            reset();
        }
        else if (e.getSource() == resetButton)
        {
            reset();
        }
        else if (e.getSource() == bell)
        {
            reset();
            clips.clear();
            loadTones("Bell");
        }
        else if (e.getSource() == glockenspiel)
        {
            reset();
            clips.clear();
            loadTones("Glockenspiel");
        }
        else if (e.getSource() == marimba)
        {
            reset();
            clips.clear();
            loadTones("Marimba");
        }
        else if (e.getSource() == oboe)
        {
            reset();
            clips.clear();
            loadTones("Oboe");
        }
        else if (e.getSource() == oh_ah)
        {
            reset();
            clips.clear();
            loadTones("Oh_Ah");
        }
        else if (e.getSource() == piano)
        {
            reset();
            clips.clear();
            loadTones("Piano");
        }
        else if (e.getSource() == save)
        {
            reset();
            saveSong();
        }
        else if (e.getSource() == load)
        {
            reset();
            loadSong();
        }

    }
    public void reset()
    {
        //Make the reset method that sets the column val to 0, playing to false, and the text on the Play/Stop button to “Play”.
        c = 0;
        isPlaying = false;
        playButton.setText("Play");
    }
    public void clear()
    {
        for (int x = 0; x < buttons.length; x++)
        {
            for (int y = 0; y < buttons[x].length; y++)
            {
                buttons[x][y].setSelected(false);
            }
        }
    }

    public void saveSong()
    {
        //Make the saveSong method that saves the curr song to a file.
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", ".txt");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            try
            {
                String path = file.getAbsolutePath();
                if (!path.endsWith(".txt"))
                {
                    path += ".txt";
                }
                String currSong = "";
                String[] noteynames = {" ","c ","b ","a-","a ","g-","g ","f-","f ","e ","d-","d ","c-","c ","b ","a-","a ","g-","g ",
                        "f-","f ","e ","d-","d ","c-","c ","b ","a-","a ","g-","g ","f-","f ","e ","d-","d ","c-","c "};
                for (int x = 0; x < buttons.length; x++)
                {
                    if (x == 0)
                    {
                        currSong += tempo + " " + buttons[0].length + "\n";
                    }
                    else
                    {
                        currSong += noteynames[x] + " ";

                        for (int y = 0; y < buttons[x].length; y++)
                        {
                            if (buttons[x-1][y].isSelected())
                            {
                                currSong += "x";
                            }
                            else
                            {
                                currSong += "-";
                            }
                        }
                        currSong += "\n";
                    }
                }
                BufferedWriter outputStream = new BufferedWriter(new FileWriter(path));
                outputStream.write(currSong);
                outputStream.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }
    public void tempSave()
    {
        oldButtons = new boolean[37][buttons[0].length];
        for (int x = 0; x < buttons.length; x++)
        {
            for (int y = 0; y < buttons[x].length; y++)
            {
                oldButtons[x][y] = buttons[x][y].isSelected();
            }
        }
    }
    public void loadSong()
    {
        //Make the loadSong method that loads a song from a file.
        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            try
            {

                File loadFile = fileChooser.getSelectedFile();

                BufferedReader inputStream = new BufferedReader(new FileReader(loadFile));

                String line = inputStream.readLine();

                String[] temp = line.split(" ");
                tempo = Integer.parseInt(temp[0]);
                int cs = Integer.parseInt(temp[1]);
                tempoBar.setValue(tempo);
                createButtons(37, cs);
                song = new Character[37][cs];
                int r = 0;
                while ((line = inputStream.readLine()) != null)
                {
                    for (int p = 0; p < cs; p++)
                    {
                        song[r][p] = line.charAt(p+2);
                    }
                    r++;
                }
                setNotes(song);
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }
    public void tempLoad()
    {
        int maxC;
        if (oldButtons[0].length > buttons[0].length)
        {
            maxC = buttons[0].length;
        }
        else
        {
            maxC = oldButtons[0].length;
        }
        for (int x = 0; x < oldButtons.length; x++)
        {
            for (int y = 0; y < maxC; y++)
            {
                buttons[x][y].setSelected(oldButtons[x][y]);
            }
        }
        revalidate();
    }
    public void addCol(int num)
    {
        //Make the addCol method that adds the specified number of columns to the song.
        tempSave();
        if (playButton.getText().equals("Stop"))
        {
            playButton.doClick();
        }
        createButtons(37, buttons[0].length + num);
        tempLoad();
    }
    public void removeCol(int num)
    {
        //Make the removeCol method that removes the specified number of columns from the song.
        tempSave();
        if (playButton.getText().equals("Stop"))
        {
            playButton.doClick();
        }
        createButtons(37, buttons[0].length - num);
        tempLoad();
    }
    public void createButtons(int rows, int cols)
    {
        if (buttonPanel != null)
        {
            scrollPane.remove(buttonPanel);
            this.remove(scrollPane);
        }
        if (cols < 0)
        {
            cols = 0;
        }
        buttonPanel = new JPanel();
        buttons = new JToggleButton[rows][cols];
        buttonPanel.setLayout(new GridLayout(rows, cols));
        System.out.println(buttons.length + " " + buttons[0].length);
        for (int x = 0; x < buttons.length; x++)
        {
            for (int y = 0; y < buttons[x].length; y++)
            {
                buttons[x][y] = new JToggleButton();
                buttons[x][y].setBackground(Color.WHITE);
                buttons[x][y].setPreferredSize(new Dimension(40, 40));
                buttons[x][y].setText(noteNames.get(x)); //name set

                buttons[x][y].setMargin(new Insets(0, 0, 0, 0));
                buttons[x][y].addActionListener(this);
                buttonPanel.add(buttons[x][y]);

            }
        }
        scrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); //scroll
        this.add(scrollPane);
    }
    public void setNotes(Character[][] song)
    {
        //Make the setNotes method that sets the notes in the song based on the song array.
        scrollPane.remove(buttonPanel);
        this.remove(scrollPane);
        scrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); //scroll
        this.add(scrollPane, BorderLayout.CENTER);
        for (int x = 0; x < buttons.length; x++)
        {
            for (int y = 0; y < buttons[x].length && y < song[x].length; y++)
            {
                if (song[x][y]!=null && song[x][y] == 'x')
                {
                    buttons[x][y].setSelected(true);
                }
                else
                {
                    buttons[x][y].setSelected(false);
                }
            }
        }
        revalidate();
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true)
        {
            try
            {
                if (isPlaying)
                {
                    for (int r = 0; r < buttons.length; r++)
                    {
                        if (buttons[r][c].isSelected())
                        {
                            //start clip
                            clips.get(r).start();
                            buttons[r][c].setBackground(Color.YELLOW);
                        }
                    }
                    Thread.sleep(tempo);
                    for (int r = 0; r < buttons.length; r++)
                    {
                        if (buttons[r][c].isSelected())
                        {
                            //stop clip
                            clips.get(r).stop();
                            clips.get(r).setFramePosition(0);
                            buttons[r][c].setBackground(Color.WHITE);
                        }
                    }
                    c++;
                    if (c == buttons[0].length - 1)
                        c = 0;
                }
                else
                {
                    Thread.sleep(tempo);
                }
            }
            catch (InterruptedException ignored)
            {

            }
        }
    }
}