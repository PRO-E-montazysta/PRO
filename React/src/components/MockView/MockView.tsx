import React, { useEffect } from 'react'
import Dialog from '../Dialog/Dialog'

const MockView = () => {
  function createInput(arg0: string, arg1: string, arg2: string) {
    return {arg0, arg1, arg2};
  }

  return (
    <div>
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
      <Dialog dialogText="TEST" confirmLabel='OKOK' inputsInfo={[createInput("A", "text", "InputA"), createInput("Beee", "text", "InputB"), createInput("Cee", "text", "InputC")]} confirmAction={undefined} cancelAction={undefined} />
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
      MockView - przejdz do pracowników w menu<br/>
    </div>
  )
}

export default MockView