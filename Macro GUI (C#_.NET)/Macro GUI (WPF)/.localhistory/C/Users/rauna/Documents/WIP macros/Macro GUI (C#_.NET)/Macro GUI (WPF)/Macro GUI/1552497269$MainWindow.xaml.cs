/* TODO

Make the thing actually do things
Add a logger

*/

using System;
using System.Windows;
using System.IO;
using System.Diagnostics;
using System.Collections.Generic;
using System.Windows.Forms;
using ComboBox = System.Windows.Controls.ComboBox;
using TextBox = System.Windows.Controls.TextBox;

namespace Macro_GUI
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        string PoDKey;
        string starDir;
        string outFilePath;
        int platformFlag;
        Dictionary<int, string> platformDict;
        string initStarDirBox;
        string starPodBox;

        public MainWindow()
        {

            InitializeComponent();
            initStarDirBox = starccm__location.Text;
            starPodBox = PoD_Box.Text;

            platformDict = new Dictionary<int, string>
            {
                { 0, "Linux" },
                { 1, "Windows" }
            };

            for (int i = 0; i < platformDict.Count; i++)
            {
                Platform.SelectedIndex = i;
                Platform.Items.Add(platformDict[i]);
            }

        }

        private void ComboBoxChange(object sender, EventArgs e)
        {
            ComboBox cmb = (ComboBox)sender;
            platformFlag = (int)cmb.SelectedIndex;
            Debug.Print(platformDict[platformFlag]);
        }

        private void runButton(object sender, EventArgs e)
        {
            PoDKey = PoD_Box.GetLineText(0);
            starDir = starccm__location.GetLineText(0);
            if (platformDict[platformFlag] == "Linux")
                runLinux();
            if (platformDict[platformFlag] == "Windows")
                runWindows();

        }

        private void boxClear(object sender1, EventArgs e)
        {
            TextBox sender = (TextBox)sender1;
            if (sender.GetLineText(0).Equals(starPodBox) || sender.GetLineText(0).Equals(initStarDirBox))
                sender.Clear();   
        }

        private void PoDNoFocus(object sender, EventArgs e)
        {
            if (removeNonAlphaNum(PoD_Box.GetLineText(0)).Equals(""))
                PoD_Box.Text = starPodBox;
        }

        private string removeNonAlphaNum(string input)
        {
            char[] output = new char[input.Length];
            char hold;
            int j = 0;
            for (int i = 0; i < input.Length; i++)
            {
                hold = input[i];
                if (Char.IsLetterOrDigit(hold))
                    output[j++] = hold;
            }
            Debug.Print(new string(output));
            return new string(output);             
        }

        private void OutputFile_Click(object sender, RoutedEventArgs e)
        { 
            OpenFileDialog fileDiag = new OpenFileDialog();
            fileDiag.ShowDialog();
            Debug.Print("File picker created");
            if (fileDiag.FileName != null)
            {
                outFilePath = fileDiag.FileName.ToString();
                filePathName.Content = outFilePath;
                Debug.Print(outFilePath);
            }
            else
                Debug.Print("fileDiag.Filename == null");
        }

        private void runLinux()
        {
            /*
             * TODO
             * Write code
             */
        }

        private void runWindows()
        {

        }

    }
}
