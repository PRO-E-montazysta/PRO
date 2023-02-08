import QRCode from 'react-qr-code';

type LabelTypes = {
  label: string;
  code: string;
}

const QRCodeLabel = ({ label, code }: LabelTypes) => {

  return (
    <div style={{textAlign: "center", width: 500, backgroundColor: "white",padding: "10px", borderRadius: "5px", marginLeft:"auto", marginRight:"auto"}}>
      <h1 style={{color: "#15171F", fontSize: 40, lineHeight: 1, marginBottom: 10, marginTop: 0, maxWidth: 500, wordWrap: "break-word"}}>
        {label}
      </h1>
        <QRCode
          value={code}
          size={192}
        />
    </div>
  );
}

export default QRCodeLabel;