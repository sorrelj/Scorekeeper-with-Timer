import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jackson Sorrells on 12/15/2016.
 */
public class Scoreboard extends JFrame implements ActionListener{

    public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static int width = gd.getDisplayMode().getWidth();
    public static int height = gd.getDisplayMode().getHeight();

    private GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    private JButton timeViewButton, teamOne, teamTwo, gamePeriod, minusOne, minusTwo, resetScores, exit, setTime,
    timeSetZero, timeAdd30Sec, timeAddOneMin, timeAddFiveMin, timeAddTenMin, timeDone;

    private Font timeFont, teamFont, infoFont, menuFont;

    private int teamOneScore, teamTwoScore, gamePeriodInt;

    private JPanel threeButtons, setTimePanel, midBottom;

    private Timer myTim;
    public static final int TENTH_SEC = 100;

    private boolean firstTime;

    private int timerTick;
    private double timTime;
    private double divNum = 10.0;
    private String timString;
    private int mins = 0, sec = 0;

    public Scoreboard(){

        timerTick = 0;  		//initial clock setting in clock ticks
        timTime = ((double)timerTick)/divNum;
        timString = new Double(timTime).toString();
        timString = "00:0"+timString;
        firstTime = true;

        this.setUndecorated(true);

        this.setTitle("Scoreboard");
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        teamOneScore = 0;
        teamTwoScore = 0;
        gamePeriodInt = 1;

        timeFont = new Font("TIMES NEW ROMAN", Font.PLAIN, 300);
        teamFont = new Font("TIMES NEW ROMAN", Font.PLAIN, 280);
        infoFont = new Font("TIMES NEW ROMAN", Font.PLAIN, 80);
        menuFont = new Font("TIMES NEW ROMAN", Font.PLAIN, 20);

        JPanel main = new JPanel(new GridLayout(2,1));
        this.add(main);

        JPanel top = new JPanel(new GridLayout(1,1));
        top.setBackground(Color.green);
        timeViewButton = new JButton("00:00.0");
        timeViewButton.setForeground(Color.white);
        timeViewButton.setBackground(Color.green);
        timeViewButton.setFont(timeFont);
        setButtonInvisible(timeViewButton);
        timeViewButton.addActionListener(this);
        top.add(timeViewButton);

        JPanel bottom = new JPanel(new GridLayout(1,3));
        JPanel left = new JPanel(new GridLayout(1,1));
        left.setBackground(Color.blue);
        teamOne = new JButton("00");
        teamOne.setBackground(Color.BLUE);
        teamOne.setForeground(Color.white);
        teamOne.setFont(teamFont);
        setButtonInvisible(teamOne);
        teamOne.addActionListener(this);
        left.add(teamOne);
        bottom.add(left);

        JPanel middle = new JPanel(new GridLayout(3,1));
        middle.setBackground(Color.green);
        JPanel periodPanel = new JPanel(new GridLayout(1,3));
        periodPanel.setBackground(Color.gray);
        JPanel fill1 = new JPanel(); fill1.setBackground(Color.gray);
        JPanel fill2 = new JPanel(); fill2.setBackground(Color.gray);
        JPanel gamePeriodPanel = new JPanel(new GridLayout(1,1));
        gamePeriodPanel.setBackground(Color.black);
        gamePeriod = new JButton("1");
        setButtonInvisible(gamePeriod);
        gamePeriod.setForeground(Color.yellow);
        gamePeriod.setFont(infoFont);
        gamePeriod.addActionListener(this);
        gamePeriodPanel.add(gamePeriod);
        periodPanel.add(fill1);
        periodPanel.add(gamePeriodPanel);
        periodPanel.add(fill2);
        middle.add(periodPanel);
        JPanel minusScores = new JPanel(new GridLayout(1,2));
        minusScores.setBackground(Color.red);//GLITCH THAT CAUSES WHITE SPACE
        JPanel teamOneMinus = new JPanel(new GridLayout(1,1));
        teamOneMinus.setBackground(Color.blue);
        minusOne = new JButton("-1");
        minusOne.setFont(infoFont);
        minusOne.setForeground(Color.white);
        setButtonInvisible(minusOne);
        minusOne.addActionListener(this);
        teamOneMinus.add(minusOne);
        minusScores.add(teamOneMinus);
        JPanel teamTwoMinus = new JPanel(new GridLayout(1,1));
        minusTwo = new JButton("-1");
        minusTwo.setFont(infoFont);
        teamTwoMinus.setBackground(Color.red);
        minusTwo.setForeground(Color.white);
        setButtonInvisible(minusTwo);
        minusTwo.addActionListener(this);
        teamTwoMinus.add(minusTwo);
        minusScores.add(teamTwoMinus);
        middle.add(minusScores);
        threeButtons = new JPanel(new GridLayout(1,3));
        threeButtons.setBackground(Color.magenta);
        JPanel resetScorePanel = new JPanel(new GridLayout(1,1));
        resetScorePanel.setBackground(Color.magenta);
        resetScores = new JButton("Reset Scores");
        resetScores.setFont(menuFont);
        resetScores.setForeground(Color.white);
        setButtonInvisible(resetScores);
        resetScores.addActionListener(this);
        resetScorePanel.add(resetScores);
        threeButtons.add(resetScorePanel);
        JPanel exitGame = new JPanel(new GridLayout(1,1));
        exit = new JButton("Exit");
        exitGame.setBackground(Color.DARK_GRAY);
        setButtonInvisible(exit);
        exit.setForeground(Color.white);
        exit.setFont(menuFont);
        exit.addActionListener(this);
        exitGame.add(exit);
        threeButtons.add(exitGame);
        JPanel setTimeButtonPan = new JPanel(new GridLayout(1,1));
        setTimeButtonPan.setBackground(Color.magenta);
        setTime = new JButton("Set Time");
        setTime.setForeground(Color.white);
        setTime.addActionListener(this);
        setTime.setFont(menuFont);
        setButtonInvisible(setTime);
        setTimeButtonPan.add(setTime);
        midBottom = new JPanel(new GridLayout(1,1));
        threeButtons.add(setTimeButtonPan);
        midBottom.add(threeButtons);
        middle.add(midBottom);
        bottom.add(middle);

        JPanel right = new JPanel(new GridLayout(1,1));
        right.setBackground(Color.red);
        teamTwo = new JButton("00");
        teamTwo.setBackground(Color.RED);
        teamTwo.setForeground(Color.white);
        teamTwo.setFont(teamFont);
        setButtonInvisible(teamTwo);
        teamTwo.addActionListener(this);
        right.add(teamTwo);
        bottom.add(right);
        bottom.setBackground(Color.green);

        main.add(top);
        main.add(bottom);

        this.pack();
        device.setFullScreenWindow(this);
        this.setVisible(true);


        menuFont = new Font("TIMES NEW ROMAN", Font.PLAIN, 15);
        setTimePanel = new JPanel(new GridLayout(1,6));
        setTimePanel.setBackground(Color.magenta);
        JPanel c1 = new JPanel(new GridLayout(1,1));
        c1.setBackground(Color.magenta);
        timeSetZero = new JButton("0");
        timeSetZero.setForeground(Color.white);
        timeSetZero.setBackground(Color.magenta);
        timeSetZero.setFont(menuFont);
        setButtonInvisible(timeSetZero);
        c1.add(timeSetZero);
        JPanel c2 = new JPanel(new GridLayout(1,1));
        c2.setBackground(Color.magenta);
        timeAdd30Sec = new JButton("+30s");
        timeAdd30Sec.setForeground(Color.white);
        timeAdd30Sec.setBackground(Color.magenta);
        timeAdd30Sec.setFont(menuFont);
        setButtonInvisible(timeAdd30Sec);
        c2.add(timeAdd30Sec);
        JPanel c3 = new JPanel(new GridLayout(1,1));
        c3.setBackground(Color.magenta);
        timeAddOneMin = new JButton("+1m");
        timeAddOneMin.setBackground(Color.magenta);
        timeAddOneMin.setForeground(Color.white);
        timeAddOneMin.setFont(menuFont);
        setButtonInvisible(timeAddOneMin);
        c3.add(timeAddOneMin);
        JPanel c4 = new JPanel(new GridLayout(1,1));
        c4.setBackground(Color.magenta);
        timeAddFiveMin = new JButton("+5m");
        timeAddFiveMin.setBackground(Color.magenta);
        timeAddFiveMin.setForeground(Color.white);
        timeAddFiveMin.setFont(menuFont);
        setButtonInvisible(timeAddFiveMin);
        c4.add(timeAddFiveMin);
        JPanel c5 = new JPanel(new GridLayout(1,1));
        c5.setBackground(Color.magenta);
        timeAddTenMin = new JButton("+10m");
        timeAddTenMin.setBackground(Color.magenta);
        timeAddTenMin.setForeground(Color.white);
        timeAddTenMin.setFont(menuFont);
        setButtonInvisible(timeAddTenMin);
        c5.add(timeAddTenMin);
        JPanel c6 = new JPanel(new GridLayout(1,1));
        c6.setBackground(Color.magenta);
        timeDone = new JButton("Done");
        timeDone.setBackground(Color.magenta);
        timeDone.setForeground(Color.white);
        timeDone.setFont(menuFont);
        setButtonInvisible(timeDone);
        c6.add(timeDone);

        timeSetZero.addActionListener(this);
        timeAdd30Sec.addActionListener(this);
        timeAddOneMin.addActionListener(this);
        timeAddFiveMin.addActionListener(this);
        timeAddTenMin.addActionListener(this);
        timeDone.addActionListener(this);

        setTimePanel.add(c1);
        setTimePanel.add(c2);
        setTimePanel.add(c3);
        setTimePanel.add(c4);
        setTimePanel.add(c5);
        setTimePanel.add(c6);

        myTim = new Timer(TENTH_SEC, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timerTick--;
                timTime = ((double)timerTick)/divNum;
                timString = new Double(timTime).toString();
                if (timerTick == 0 && mins > 0){
                    mins--;
                    timerTick = 599;
                    timTime = ((double)timerTick)/divNum;
                    timString = new Double(timTime).toString();
                }
                if (timerTick < 100){
                    timString = "0"+ timString;
                }
                if (mins < 10){
                    timString = "0"+mins+":"+timString;
                }else{
                    timString = mins+":"+timString;
                }
                if (mins == 0 && timerTick == 0){
                    timString = "00:00.0";
                    myTim.stop();
                    System.out.println("Stopped");
                    timeViewButton.setForeground(Color.RED);
                    timeViewButton.setText(timString);
                    firstTime = true;
                    mins = 0;
                    sec = 0;
                    timerTick = 0;
                    PlaySound();
                }
                timeViewButton.setText(timString);

            }
        });
    }

    private void setButtonInvisible(JButton b){
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setOpaque(false);
        b.setBorderPainted(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JButton){
            JButton button = (JButton) source;

            if (button == exit){
                System.exit(342342);
            }else if (button == teamOne){
                teamOneScore++;
                if (teamOneScore < 10)
                    teamOne.setText("0"+teamOneScore);
                else
                    teamOne.setText(""+teamOneScore);
            }else if (button == teamTwo){
                teamTwoScore++;
                if (teamTwoScore < 10)
                    teamTwo.setText("0"+teamTwoScore);
                else
                    teamTwo.setText(""+teamTwoScore);
            }else if (button == minusOne){
                if (teamOneScore > 0) {
                    teamOneScore--;
                    if (teamOneScore < 10)
                        teamOne.setText("0" + teamOneScore);
                    else
                        teamOne.setText("" + teamOneScore);
                }
            }else if (button == minusTwo){
                if (teamTwoScore > 0) {
                    teamTwoScore--;
                    if (teamTwoScore < 10)
                        teamTwo.setText("0" + teamTwoScore);
                    else
                        teamTwo.setText("" + teamTwoScore);
                }
            }else if (button == resetScores){
                teamOneScore = 0;
                teamTwoScore = 0;
                gamePeriodInt = 1;
                teamOne.setText("0"+teamOneScore);
                teamTwo.setText("0"+teamTwoScore);
                gamePeriod.setText(""+gamePeriodInt);
            }else if (button == gamePeriod){
                if (gamePeriodInt < 9){
                    gamePeriodInt++;
                    gamePeriod.setText(""+gamePeriodInt);
                }
            }else if (button == setTime){
                if (!myTim.isRunning()){
                    midBottom.remove(0);
                    midBottom.add(setTimePanel);
                    this.repaint();
                }
            }else if (button == timeDone) {
                midBottom.remove(0);
                midBottom.add(threeButtons);
                this.repaint();
            }else if (button == timeViewButton){
                if (myTim.isRunning()){
                    myTim.stop();
                }else{
                    if (mins >0 || sec > 0){
                        midBottom.remove(0);
                        midBottom.add(threeButtons);
                        this.repaint();
                        if (firstTime){
                            timerTick = sec * 10;
                            if (sec == 0){
                                timerTick = 1;
                            }
                        }
                        firstTime = false;
                        myTim.start();
                        timeViewButton.setForeground(Color.white);
                    }
                }
            }else if (button == timeSetZero){
                firstTime = true;
                timeViewButton.setForeground(Color.white);
                mins = 0;
                timerTick = 0;
                sec = 0;
                timeViewButton.setText("00:00.0");
            }else if (button == timeAdd30Sec){
                timeViewButton.setForeground(Color.white);
                if (mins < 99){
                    sec += 30;
                    if (sec == 60){
                        mins++;
                        sec = 0;
                    }
                }
                updateTime();

            }else if (button == timeAddOneMin){
                timeViewButton.setForeground(Color.white);
                if (mins < 99){
                    mins += 1;
                }
                updateTime();
            }else if (button == timeAddFiveMin){
                timeViewButton.setForeground(Color.white);
                if (mins < 95){
                    mins +=5;
                }
                updateTime();
            }else if (button == timeAddTenMin){
                timeViewButton.setForeground(Color.white);
                if (mins < 90){
                    mins+=10;
                }
                updateTime();
            }
        }

    }


    private void updateTime(){
        if (mins < 10){
            if (sec < 30){
                timeViewButton.setText("0"+mins + ":" + "00" + ".0");
            }else{
                timeViewButton.setText("0"+mins + ":" + sec + ".0");
            }
        }else{
            if (sec < 30){
                timeViewButton.setText(mins + ":" + "00" + ".0");
            }else{
                timeViewButton.setText(mins + ":" + sec + ".0");
            }
        }
    }


    public void PlaySound(){
        try {
            File soundFile = new File("<PATH TO FILE>");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);
            clip.start();

        }catch (IOException e){
             e.printStackTrace();
        }catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        }catch (LineUnavailableException e){
            e.printStackTrace();
        }

    }


}
