Imports System.Text

Public Class Form1

    Private xmlDataBase As String = My.Application.Info.DirectoryPath & "\xmlData.xml"

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load

        If My.Computer.FileSystem.FileExists(xmlDataBase) = True Then
            DataSet1.ReadXml(xmlDataBase)
        End If
    End Sub

    Private Sub ContactsBindingNavigatorSaveItem_Click(sender As Object, e As EventArgs) Handles ContactsBindingNavigatorSaveItem.Click
        Me.Validate()
        ContactsBindingSource.EndEdit()
        DataSet1.WriteXml(xmlDataBase)
    End Sub

    Private Sub Button1_Click(sender As Object, e As EventArgs)
        Dim testVariabile As String = " Variabile inizializzata"
        If (testVariabile IsNot String.Empty) Then
            System.Console.WriteLine(testVariabile)
        End If
    End Sub

    Public Class FakeSogg
        Private rng As Random = New Random()

        Private defaultStr As String = "XXXXXXXXXX"
        Private defaultIVA As String = "00000000000"
        Private defTelMatr As String = "1111111111"
        Private defCCcarta As String = "111111111111"
        Private defIBANend As String = "111111111111"
        Private defEmail As String = "xxx@xxx.com"

        Public ReadOnly pDSCOGNOME As String
        Public ReadOnly pDSNOME As String
        Public ReadOnly pCDFISC As String
        Public ReadOnly pCDPIVA As String
        Public ReadOnly pDTNASC As Date
        Public ReadOnly pDTNASCs As String()
        Public ReadOnly pCDSESSO As Char
        Public ReadOnly pLOCATION As FakeLoc
        Public ReadOnly pNUDOC As String
        Public ReadOnly pNUTEL As String
        Public ReadOnly pCDCOORDCLI As String
        Public ReadOnly pEMAIL As String

        Public Sub New()
            pDSCOGNOME = randomStringa()
            pDSNOME = randomStringa()

            pDTNASC = randomDTNASC()
            pDTNASCs = Split((Split(pDTNASC.ToString(), " ")(0)), "/")
            pCDSESSO = CChar(If(rng.Next(0, 2) > 0, "M", "F"))

            pLOCATION = New FakeLoc()

            pCDFISC = generaCodiceFiscale(pDSNOME, pDSCOGNOME, pCDSESSO, pDTNASC, pLOCATION.pCDCATAST)

            pCDPIVA = defaultIVA
            pNUDOC = defaultStr
            pNUTEL = defTelMatr
            pCDCOORDCLI = defIBANend
            pEMAIL = defEmail
        End Sub

        Public Overrides Function ToString() As String
            Return String.Format("[{0}, {1} {2}]", pCDFISC, pDSCOGNOME, pDSNOME)
        End Function

        Private Function randomStringa() As String
            Dim alfabeto As String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            Dim sb As New StringBuilder

            For i As Integer = 1 To 10
                Dim idx As Integer = rng.Next(0, alfabeto.Length - 1)
                sb.Append(alfabeto.Substring(idx, 1))
            Next

            Return sb.ToString()
        End Function

        Private Function randomDTNASC() As Date
            Dim date1 As Date = CDate("01/01/1961")
            Dim date2 As Date = CDate("12/12/1999")
            Dim dDiff As Long = DateDiff("d", date1, date2, Constants.vbMonday)
            Dim rngDate As Date

            Randomize()
            rngDate = DateAdd("d", Int((dDiff * Rnd()) + 1), date1)

            Return rngDate
        End Function

        Private Function generaCodiceFiscale(ByVal DSNOME As String, ByVal DSCOGNOME As String, ByVal CDSESSO As Char, ByVal DTNASC As Date, ByVal CDCATAST As String) As String
            Dim newCF As String = ""
            Dim mesiCD As New Dictionary(Of String, String) From {{"01", "A"}, {"02", "B"}, {"03", "C"}, {"04", "D"}, {"05", "E"}, {"06", "H"}, {"07", "L"}, {"08", "M"}, {"09", "P"}, {"10", "R"}, {"11", "S"}, {"12", "T"}}
            Dim builder As StringBuilder = New StringBuilder()

            Dim dateStr As String() = Split((Split(DTNASC.ToString(), " ")(0)), "/")

            builder.Append(getConsonanti(DSCOGNOME, False)) ' cognome

            builder.Append(getConsonanti(DSNOME, True)) ' nome

            builder.Append(dateStr(2).Substring(2, 2)) ' anno

            builder.Append(mesiCD.Item(dateStr(1))) ' mese

            Dim tempDay As String = If(CDSESSO = "M", dateStr(0), CStr(CInt(dateStr(0)) + 40)) ' giorno
            If (tempDay.Count < 2) Then
                tempDay = "0" & tempDay
            End If
            builder.Append(tempDay)

            builder.Append(CDCATAST) ' luogo

            builder.Append(getCharControllo(builder.ToString)) ' controllo

            newCF = builder.ToString()
            Return newCF
        End Function

        Private Function getConsonanti(ByVal nomeCognome As String, ByVal isNome As Boolean) As String
            Dim vocali As List(Of String) = New List(Of String)
            Dim consonanti As List(Of String) = New List(Of String)
            Dim mods As Integer = If(isNome, 1, 0)
            Dim builder As System.Text.StringBuilder = New StringBuilder()
            Dim i As Integer = 0

            For Each c As Char In nomeCognome
                If "AEIOU".Contains(c) Then
                    vocali.Add(c)
                Else
                    consonanti.Add(c)
                End If
            Next

            While (consonanti.Count < 3)
                consonanti.Add(If(vocali.Count < i, vocali(i), "X"))
                i = i + 1
            End While
            If (consonanti.Count = 3) Then
                For Each c As String In consonanti
                    builder.Append(c)
                Next
            Else
                builder.Append(consonanti(0))
                builder.Append(consonanti(mods + 1))
                builder.Append(consonanti(mods + 2))
            End If

            Return builder.ToString()
        End Function

        Private Function getCharControllo(ByVal cfPart As String) As Char
            Dim sum As Integer = 0
            Dim charCtrl As New Dictionary(Of String, Short()) From {{"0", {1, 0}}, {"A", {1, 0}}, {"1", {0, 1}}, {"B", {0, 1}}, {"2", {5, 2}}, {"C", {5, 2}}, {"3", {7, 3}}, {"D", {7, 3}}, {"4", {9, 4}}, {"E", {9, 4}}, {"5", {13, 5}}, {"F", {13, 5}}, {"6", {15, 6}}, {"G", {15, 6}}, {"7", {17, 7}}, {"H", {17, 7}}, {"8", {19, 8}}, {"I", {19, 8}}, {"9", {21, 9}}, {"J", {21, 9}}, {"K", {2, 10}}, {"L", {4, 11}}, {"M", {18, 12}}, {"N", {20, 13}}, {"O", {11, 14}}, {"P", {3, 15}}, {"Q", {6, 16}}, {"R", {8, 17}}, {"S", {12, 18}}, {"T", {14, 19}}, {"U", {16, 20}}, {"V", {10, 21}}, {"W", {22, 22}}, {"X", {25, 23}}, {"Y", {24, 24}}, {"Z", {23, 25}}}
            Dim resto As Integer

            For idx As Integer = 0 To cfPart.Count - 1 Step 2
                sum = sum + charCtrl.Item(cfPart(idx))(0)
            Next
            For idx As Integer = 1 To cfPart.Count - 1 Step 2
                sum = sum + charCtrl.Item(cfPart(idx))(1)
            Next
            resto = sum Mod 26

            Return Chr(Asc("A") + resto)
        End Function
    End Class

    Public Class FakeLoc

        Public pCDCATAST As String
        Public pDSCOMUNE As String
        Public ReadOnly pDSVIA As String = "XXXXXXXXXX"

        Public Sub New()
            fetchRngLocation()
        End Sub

        Private Sub fetchRngLocation()
            Dim dict As String(,) =
            {{"A271", "Ancona"},
            {"A326", "Aosta"},
            {"A345", "L'Aquila"},
            {"A662", "Bari"},
            {"A944", "Bologna"},
            {"B354", "Cagliari"},
            {"B519", "Campobasso"},
            {"C352", "Catanzaro"},
            {"D612", "Firenze"},
            {"D969", "Genova"},
            {"F205", "Milano"},
            {"F839", "Napoli"},
            {"G273", "Palermo"},
            {"G478", "Perugia"},
            {"G942", "Potenza"},
            {"H501", "Roma"},
            {"L219", "Torino"},
            {"L378", "Trento"},
            {"L424", "Trieste"},
            {"L736", "Venezia"}}

            Dim Rand As New Random()
            Dim Index As Integer = Rand.Next(1, dict.Length / 2 - 1)

            pCDCATAST = dict(Index, 0)
            pDSCOMUNE = dict(Index, 1)

        End Sub
    End Class


    Private Sub ToolStripButton1_Click(sender As Object, e As EventArgs) Handles ToolStripButton1.Click
        Dim fakeSogg As FakeSogg = New FakeSogg()
        NameTextBox.Text = fakeSogg.pDSNOME & " " & fakeSogg.pDSCOGNOME
        Codice_FiscaleTextBox.Text = fakeSogg.pCDFISC
        MaskedTextBox1.Text = fakeSogg.pNUTEL
    End Sub

End Class
