import { useRef } from 'react';
import ReactToPrint from 'react-to-print';
import QRCodeLabel from './QRCodeLabel';

type LabelTypes = {
  label: string;
  code: string;
}

const PrintQRCodeLabel = ({ label, code }: LabelTypes) => {
  const componentRef = useRef(null);

  return (
    <div>
      <ReactToPrint
        trigger={() => <button>Wydrukuj etykietę</button>}
        content={() => componentRef.current}
      />
      <div  style={{display: "none"}}>
        <div ref={componentRef}>
          <QRCodeLabel label={label} code={code}/>
        </div>
      </div>
    </div>
  );
};

export default PrintQRCodeLabel;