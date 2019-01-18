Imports System.Text.RegularExpressions
Imports System.Xml

Module SanitizeBadXml
    Public Function sanitizzaEscapeXML(ByVal inputStr As String, Optional ByVal onlyAmp As Boolean = False) As String
        If (inputStr = String.Empty) OrElse (inputStr.IndexOfAny({Chr(38), Chr(39), Chr(60), Chr(62), Chr(34)})) = -1 Then
            Return inputStr
        End If

        inputStr = inputStr.Replace("&", "&amp;")

        If Not onlyAmp Then
            inputStr = inputStr.Replace("'", "&apos;").Replace("<", "&lt;").Replace(">", "&gt;").Replace(Chr(34), "&quot;")
        End If

        Dim patternIn As String = "&((amp;)|(lt;)|(gt;)|(apos;)|(quot;)|(#[\d]+;))+"
        Dim patternOut As String = "&$1"
        Dim reg As Regex = New Regex(patternIn)
        Dim outString As String = reg.Replace(inputStr, patternOut)

        Return outString
    End Function


    Public Function testXmlDocument() As String
        Dim doc As New XmlDocument
        doc.LoadXml($"<book ISBN='1-111111-57-5'> 
            <title>C# &amp;amp; JavaScript</title> 
            <price>9.9</price> 
            <reviews>
                <comment>
                    <user>Giovanni' Tizo</user>
                    <text>C# &amp;amp; JS mi e' piaciuto</text>
                </comment>
                <comment>
                    <user>Bucc&amp;gt;dev&amp;lt;</user>
                    <text>NoStarch Press is the best!</text>
                </comment>
                <comment>
                    <user>Software &amp; Books</user>
                    <text>Really in depth, reference book!&amp;#96;</text>
                </comment>
            </reviews> 
        </book>")

        Dim root As XmlNode = doc.FirstChild
        Dim text As String = doc.OuterXml

        Return sanitizzaEscapeXML(text, True)
    End Function
    Sub Main()

        Console.WriteLine("&" + CStr(Asc("&")))
        Console.WriteLine("'" + CStr(Asc("'")))
        Console.WriteLine(">" + CStr(Asc(">")))
        Console.WriteLine("<" + CStr(Asc("<")))

        Dim test1 As String = "Giovanni&apos; Tizo"
        Dim test2 As String = "&amp;amp;amp;"
        Dim test3 As String = "Tizio & company"
        Dim test4 As String = "&amp;amp;amp;amp;lt;"
        Dim test5 As String = "&amp;apos;"
        Dim test6 As String = "&quot;"
        Dim test7 As String = $"Giovanni' & {Chr(34)}devs{Chr(34)}"
        Dim test8 As String = $"Giovann&#192; & {Chr(34)}devs{Chr(34)}"
        Dim test9 As String = "Casa dolce casa"
        Dim test10 As String = String.Empty

        Console.WriteLine($"TEST1 {test1}")
        Console.WriteLine($"RESULT: {sanitizzaEscapeXML(test1)}")
        Console.WriteLine($"TEST2 {test2}")
        Console.WriteLine($"RESULT: {sanitizzaEscapeXML(test2)}")
        Console.WriteLine($"TEST3 {test3}")
        Console.WriteLine($"RESULT: {sanitizzaEscapeXML(test3)}")
        Console.WriteLine($"TEST4 {test4}")
        Console.WriteLine($"RESULT: {sanitizzaEscapeXML(test4)}")
        Console.WriteLine($"TEST5 {test5}")
        Console.WriteLine($"RESULT: {sanitizzaEscapeXML(test5)}")
        Console.WriteLine($"TEST6 {test6}")
        Console.WriteLine($"RESULT: {sanitizzaEscapeXML(test6)}")
        Console.WriteLine($"TEST7 {test7}")
        Console.WriteLine($"RESULT: {sanitizzaEscapeXML(test7)}")
        Console.WriteLine($"TEST8 {test8}")
        Console.WriteLine($"RESULT: {sanitizzaEscapeXML(test8)}")
        Console.WriteLine($"TEST9 {test9}")
        Console.WriteLine($"RESULT: {sanitizzaEscapeXML(test9)}")
        Console.WriteLine($"TEST10 {test10}")
        Console.WriteLine($"RESULT: {sanitizzaEscapeXML(test10)}")

        Console.WriteLine($"TEST XML")
        Console.WriteLine($"RESULT: {testXmlDocument()}")
        Console.ReadLine()
    End Sub

End Module